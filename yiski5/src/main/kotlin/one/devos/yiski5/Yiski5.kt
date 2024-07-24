package one.devos.yiski5

import dev.minn.jda.ktx.events.listener
import dev.minn.jda.ktx.messages.Embed
import dev.minn.jda.ktx.messages.send
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski5.data.SerializedHistory
import one.devos.yiski5.data.Yiski5ConfigData
import xyz.artrinix.aviation.Aviation
import java.text.SimpleDateFormat
import java.time.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.time.Duration.Companion.minutes

val logger = KotlinLogging.logger { }

// all of this needs to be decoupled and not entirely thrown into the companion object to make this actually work with the new module loader
@OptIn(YiskiModule::class)
class Yiski5 : YiskiModuleEntrypoint {
    companion object {
        lateinit var instance: Yiski5
            private set

        lateinit var config: Yiski5ConfigData
            private set

        lateinit var timezone: ZoneId
            private set

        val json = Json { this.prettyPrint = true }

        // Calculates the next time as a getter.
        val resetTime: Date
            get() {
                val destinationTime = LocalDateTime.of(LocalDate.now().plusDays(config.bot.daysAhead), LocalTime.of(config.bot.initialResetHour.toInt(), config.bot.initialResetMinute.toInt()))
                return Date.from(destinationTime.atZone(timezone).toInstant())
            }

        val dateNow: Date
            get() = Date.from(Instant.now())

        fun setTimer(jda: JDA): TimerTask {
            logger.info { "Vent channel is set to wipe on $resetTime" }
            return Timer().schedule(resetTime, config.bot.resetInterval.minutes.inWholeMilliseconds) {
                logger.info { "Vent channel wipe initiated at $dateNow" }
                clearVentChannel(jda)
            }
        }

        fun clearVentChannel(jda: JDA) {
            val ventChannel = jda.getTextChannelById(config.channels.vent) ?: return logger.error("Vent channel <${config.channels.vent}> doesn't exist.")
            val ventLogChannel = jda.getTextChannelById(config.channels.ventLog) ?: return logger.error("Vent log channel <${config.channels.ventLog}> doesn't exist.")
            val ventAttachmentChannel = jda.getTextChannelById(config.channels.ventAttachments) ?: return logger.error("Vent attachment channel <${config.channels.ventAttachments}> doesn't exist.")
            val history = mutableListOf<Message>()
            val getHistory = ventChannel.getHistoryFromBeginning(100).complete()

            history.addAll(getHistory.retrievedHistory)
            history.addAll(getHistory.retrieveFuture(100).complete())

            var collectedHistory = history
                .filterNot { config.filters.messages.contains(it.idLong) }
                .filterNot { config.filters.authors.contains(it.author.idLong) }
                .asReversed()

            if (config.filters.pinned) collectedHistory = collectedHistory.filterNot { it.isPinned }
            if (config.filters.webhooks) collectedHistory = collectedHistory.filterNot { it.isWebhookMessage }
            if (config.filters.bots) collectedHistory = collectedHistory.filterNot { it.author.isBot }
            if (config.filters.system) collectedHistory = collectedHistory.filterNot { it.author.isSystem }

            // return if the channelHistory is empty, in consequence this will not trigger a log.
            if (collectedHistory.isEmpty()) return

            val serializedHistory = collectedHistory.map {
                SerializedHistory.SerializedMessage(
                    messageId = it.idLong,
                    authorId = it.author.idLong,
                    authorDisplay = it.author.effectiveName,
                    authorName = it.author.name,
                    content = it.contentRaw,
                    embeds = it.embeds.count(),
                    attachments = it.attachments.count()
                )
            }

            val data = SerializedHistory(
                data = SerializedHistory.SerializedData(
                    channelName = ventChannel.name,
                    channelId = ventChannel.idLong,
                    serverId = ventChannel.guild.idLong,
                    messageCount = collectedHistory.count(),
                    date = LocalDate.now(timezone).toString()
                ),
                messages = serializedHistory
            )

            val encodedData: String = json.encodeToString(SerializedHistory.serializer(), data)

            logger.debug { "Encoded JSON data of the vent: \n$encodedData" }

            val messageAttachments = collectedHistory.filter { it.attachments.size > 0 }.map { message ->
                message to message.attachments
                    .filter { it.size <= ventAttachmentChannel.guild.boostTier.maxFileSize }
                    .map { it.fileName to it.proxy.download().get() }
            }

            messageAttachments.forEach { (message, attachments) ->
                ventAttachmentChannel.send(
                    embeds = listOf(Embed {
                        author {
                            name = "${message.author.name} <${message.author.id}>"
                            iconUrl = message.author.effectiveAvatarUrl
                        }
                        title = "Message ID: ${message.id}"
                        if (attachments.isEmpty()) description = "File was either too large to send in this server or was deleted during purge."
                    }),
                    files = attachments.map { (name, file) -> FileUpload.fromData(file, name) }
                ).complete()
            }

            val formattedDate = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Date())

            ventLogChannel.send(
                content = "Vent log for $formattedDate",
                files = listOf(FileUpload.fromData(encodedData.encodeToByteArray(), "$formattedDate.json"))

            ).complete()

            try {
                val messagesOverTwoWeeks = collectedHistory.filter { it.timeCreated.isBefore(OffsetDateTime.now().minusDays(14)) }
                val messages = collectedHistory.filterNot { it.timeCreated.isBefore(OffsetDateTime.now().minusDays(14)) }

                if (messages.size > 1) {
                    val messageChunks = messages.chunked(80)
                    messageChunks.forEachIndexed { index, chunk ->
                        try {
                            ventChannel.deleteMessages(chunk).queue()
                        } catch (e: Exception) {
                            logger.error(e) { "Something went wrong trying to bulk-delete chunk $index/${messageChunks.size}" }
                            ventLogChannel.send(embeds = listOf(
                                Embed {
                                    title = "Developer intervention required"
                                    description = "Failed to delete bulk-delete chunk $index/${messageChunks.size}, please refer to the logs."
                                }
                            )).queue()
                        }
                    }
                } else {
                    ventChannel.deleteMessageById(messages.first().id).complete()
                }

                if (messagesOverTwoWeeks.isNotEmpty()) {
                    ventLogChannel.send(embeds = listOf(
                        Embed {
                            title = "Admin intervention required"
                            description = "Messages over 2 weeks have been detected in <#${config.channels.vent}>, manual deletion is required."
                        }
                    )).queue()
                }
            } catch (e: Exception) {
                logger.error("A fatal error has occurred whilst trying to delete messages", e)
                ventLogChannel.send(embeds = listOf(
                    Embed {
                        title = "Developer intervention required"
                        description = "Something has gone horrible wrong, please check the logs"
                    }
                )).queue()
            }
        }
    }

    init {
        instance = this
        timezone = ZoneId.of(config.bot.timezone)
        logger.info { "Yiski5 module loaded." }
    }

    override fun config() {
    }

    override fun database(database: DatabaseManager) { }


    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski5.commands")
    }

    override fun jda(jda: JDA) {
        // Ready Event
        jda.listener<ReadyEvent> {
            // Mayhem starts here
            // Let the Dragons sleep else there will be a fire in the server closet (in this case, an RPI 4)
            setTimer(jda)
        }
    }
}