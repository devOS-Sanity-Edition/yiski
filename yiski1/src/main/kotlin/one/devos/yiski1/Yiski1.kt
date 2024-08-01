package one.devos.yiski1

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.events.listener
import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent
import net.dv8tion.jda.api.events.session.ReadyEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands
import one.devos.yiski.common.YiskiConstants
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski1.data.Yiski1ConfigData
import one.devos.yiski1.tables.guild.Guild
import org.jetbrains.exposed.sql.transactions.transaction
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

@YiskiModule
class Yiski1(
    // Change these to vals if they're needed!
    database: DatabaseManager,
    aviation: Aviation,
    private val jda: JDA,
    private val config: Yiski1ConfigData
) : YiskiModuleEntrypoint(
    database,
    aviation,
    jda,
    config
) {

    companion object {
        lateinit var instance: Yiski1
            private set
    }

    init {
        instance = this
    }

    override fun setup() {
        logger.info { "Yiski1 module set up." }

        jda.listener<ReadyEvent> {
//            for (guild in it.jda.guilds) { // initial attempts failed
//                try {
//                    Guild.createMuteRole(guild.idLong, config.moderation.muteRoleID)
//                } catch (e: Exception) {
//                    logger.error(e) { "Table is failing to be populated..." }
//                }
//            }
            jda.updateCommands().addCommands(
                Commands.message("Pin Message"),
                Commands.message("Unpin Message")
            ).queue()

            transaction {
                Guild.findById(YiskiConstants.config.discord.homeGuildID) ?: Guild.new(YiskiConstants.config.discord.homeGuildID) {
                    muteRole = config.moderation.muteRoleID
                }
            }
        }

        jda.listener<MessageContextInteractionEvent> { event ->
            if (event.name == "Pin Message") {
                if (event.channel?.asThreadChannel()?.ownerId == event.member?.id) {
                    event.target.pin().await()  // IM GOING TO FUCKING BITE THE FUCKIN CURB FUCKIN HELL
                                                // I SPENT TOO LONG BEFORE DEFTU SWOOPED IN ON SOMETHING I ALREADY FUCKING TRIED
                                                // AAAAAAAAAAAAAAAAAAAAAAAAAA
                    event.reply("Pinned!").setEphemeral(true).queue()
                } else {
                    event.reply("You are not the thread owner.").setEphemeral(true).queue()
                }
            }

            if (event.name == "Unpin Message") {
                if (event.channel?.asThreadChannel()?.ownerId == event.member?.id) {
                    event.target.unpin().await()
                    event.reply("Unpinned!").setEphemeral(true).queue()
                } else {
                    event.reply("You are not the thread owner.").setEphemeral(true).queue()
//                    logger.debug { event.channel?.asThreadChannel()?.isOwner }
                }
            }
        }

        // TODO!
    }

}
