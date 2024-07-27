package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import dev.minn.jda.ktx.messages.InlineEmbed
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski1.logger
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionMessage
import one.devos.yiski1.tables.moderation.InfractionType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.util.*
import java.util.concurrent.TimeUnit

class Ban : Scaffold {
    private companion object {
        suspend fun TextChannel._getMessagesAsInfractions(count: Int, authorId: Long): List<String> {
            val messageHistory = this.getHistoryBefore(this.latestMessageIdLong, count).await().retrievedHistory.filter { it.author.idLong == authorId }

            return messageHistory.map {
                Json.encodeToString(
                    InfractionMessage.serializer(),
                    InfractionMessage(
                        it.author.idLong,
                        it.author.asTag,
                        it.idLong,
                        it.contentRaw,
                        it.timeCreated.toEpochSecond()
                    )
                )
            }
        }
    }


    private val banTimeFrameConversion: Map<Char, TimeUnit> = mapOf(
        's' to TimeUnit.SECONDS,
        'm' to TimeUnit.MINUTES,
        'h' to TimeUnit.HOURS,
        'd' to TimeUnit.DAYS,
    )

    private val durationRegex = "(?<duration>\\d+)(?<unit>[smhd])".toRegex()

    @SlashCommand(name = "ban", description = "Ban a user", defaultUserPermissions = [Permission.BAN_MEMBERS, Permission.KICK_MEMBERS, Permission.MODERATE_MEMBERS], guildOnly = true)
    suspend fun ban(ctx: SlashContext,
                    @Description("Which user?") member: Member,
                    @Description("What's the reasoning?") reason: String,
                    @Description("Enter the amount of time you want to wipe a person's messages. [ex: 7d, 5h, 1m, or 0m]") timeframe: String?) {
//        val bannedUserID = member.user.id
        val response = ctx.interaction.deferReply().await()

        fun InlineEmbed.errorEmbedRegexFailure() {
            title = "Doesn't match regex! Input was $timeframe"
            description = "Are you sure you put it in the format of Number and then Time Unit, like `7d`, `5h`, `1m`, or `0m`?"
            color = EmbedHelpers.failColor()
            footer("mild oopsies moment. if you believe this is a bug, ping aubs.")
        }

        fun InlineEmbed.errorEmbedConversionFailure() {
            title = "Conversion failed! Input was $timeframe"
            description = "Are you sure you put it in the format of Number and then Time Unit, like `7d`, `5h`, `1m`, or `0m`?"
            color = EmbedHelpers.failColor()
            footer("mild oopsies moment. if you believe this is a bug, ping aubs.")
        }

        val purgeTime: Pair<Int, TimeUnit> = if (timeframe != null) {
            val timeframeMatch = durationRegex.matchEntire(timeframe)
            if (timeframeMatch == null) {
                response.editOriginalEmbeds(Embed {
                    errorEmbedRegexFailure()
                }).await()
                return logger.error { "Doesn't match regex. Input was $timeframe" }
            }

            val timeframeDuration = timeframeMatch.groups["duration"]?.value?.toIntOrNull()
            if (timeframeDuration == null) {
                response.editOriginalEmbeds(Embed {
                    errorEmbedRegexFailure()
                }).await()
                return logger.error { "Doesn't match regex. Input was $timeframe" }
            }

            val timeframeUnit = timeframeMatch.groups["unit"]?.value
            if (timeframeUnit == null) {
                response.editOriginalEmbeds(Embed {
                    errorEmbedRegexFailure()
                }).await()
                return logger.error { "Doesn't match regex. Input was $timeframe" }
            }

            val conversion = banTimeFrameConversion[timeframeUnit[0]]
            if (conversion == null) {
                response.editOriginalEmbeds(Embed {
                    errorEmbedConversionFailure()
                }).await()
                return logger.error { "Conversion failed, returned null. Input was $timeframe" }
            }

            Pair(timeframeDuration, conversion)
        } else {
            Pair(0, TimeUnit.SECONDS)
        }

        fun returnNullTimeframeAs0(): String {
            if (timeframe != null) {
                return timeframe
            } else {
                return "uh... never. :p"
            }
        }

        val messages = ctx.textChannel!!._getMessagesAsInfractions(30, ctx.author.idLong)

        newSuspendedTransaction {
            Infraction.new {
                this.guildId = ctx.guild!!.idLong
                this.userId = member.idLong
                this.type = InfractionType.BAN
                this.reason = reason
                this.moderator = ctx.author.idLong
                this.messages = messages
                this.roles = member.roles.map { it.idLong }
                this.createdAt = System.currentTimeMillis()
                this.duration = 0
            }
        }

        ctx.guild!!.ban(member, purgeTime.first, purgeTime.second).reason(reason).await()

        response.editOriginalEmbeds(Embed {
            title = "The Ban Hammer has been swung!"
            color = EmbedHelpers.moderationColor()
            field("User", member.user.name, true)
            field("User ID:", "`${member.user.id}`", true )
            field("Reason", reason, true)
            field("Timeframe of messages wiped", "Last ${returnNullTimeframeAs0()}", true)
        }).await()
    }
}