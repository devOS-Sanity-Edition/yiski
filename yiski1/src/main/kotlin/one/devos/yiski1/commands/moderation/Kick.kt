package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.UserSnowflake
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionMessage
import one.devos.yiski1.tables.moderation.InfractionType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Kick : Scaffold {
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

    @SlashCommand(name = "kick", description = "Kicks a user", defaultUserPermissions = [Permission.BAN_MEMBERS, Permission.KICK_MEMBERS, Permission.MODERATE_MEMBERS])
    suspend fun kick(ctx: SlashContext, @Description("Which user?") member: Member, @Description("What's the reason?") reason: String) {
        val messages = ctx.textChannel!!._getMessagesAsInfractions(30, ctx.author.idLong)

        newSuspendedTransaction {
            Infraction.new {
                this.guildId = ctx.guild!!.idLong
                this.userId = member.idLong
                this.type = InfractionType.KICK
                this.reason = reason
                this.moderator = ctx.author.idLong
                this.messages = messages
                this.roles = member.roles.map { it.idLong }
                this.createdAt = System.currentTimeMillis()
                this.duration = 0
            }
        }

        ctx.guild?.kick(member)?.reason(reason)?.queue()

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "The Boot has kicked someone!"
                color = EmbedHelpers.moderationColor()
                field("User", member.user.name, false)
                field("User ID", "`${UserSnowflake.fromId(member.id).id}`")
                field("Reason", reason, false)
            }).queue()
    }
}