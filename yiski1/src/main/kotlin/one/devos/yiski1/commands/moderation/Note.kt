package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.entities.Member
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Note : Scaffold {
    @SlashCommand(name = "note", description = "Adds an Infraction Note on a user")
    suspend fun note(ctx: SlashContext, @Description("Which user?") member: Member, @Description("What's the note?") note: String) {
        val interaction = ctx.interaction.deferReply().await()

        val infraction = newSuspendedTransaction {
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

        interaction
            .editOriginalEmbeds(Embed {
                author("Note taken on @${member.user.name} (${member.id})", null, member.effectiveAvatarUrl)
                title = "#${infraction.id}"
                description = note
                color = EmbedHelpers.moderationColor()
                footer("Moderator: ${ctx.interaction.user.name} (${ctx.interaction.user.idLong})", ctx.interaction.member?.effectiveAvatarUrl ?: ctx.interaction.user.effectiveAvatarUrl)
            })
            .await()
    }
}