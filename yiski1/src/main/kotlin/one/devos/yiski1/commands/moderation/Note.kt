package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import kotlinx.serialization.json.Json
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

class Note : Scaffold {
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

    @SlashCommand(name = "note", description = "Adds an Infraction Note on a user")
    suspend fun note(ctx: SlashContext, @Description("Which user?") member: Member, @Description("What's the note?") note: String ) {
        newSuspendedTransaction {
            Infraction.new {
                this.guildId = ctx.guild!!.idLong
                this.userId = member.idLong
                this.type = InfractionType.NOTE
                this.reason = note
                this.moderator = ctx.author.idLong
                this.messages = emptyList()
                this.roles = emptyList()
                this.createdAt = System.currentTimeMillis()
                this.duration = 0
            }
        }

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "Note taken!"
                color = EmbedHelpers.moderationColor()
                field("User", member.user.name, true)
                field("User ID", "`${member.user.id}`", true )
                field("Moderator Note", note, false)
            }).queue()
    }
}