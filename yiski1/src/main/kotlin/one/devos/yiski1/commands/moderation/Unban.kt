package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.UserSnowflake
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Unban : Scaffold {
    @SlashCommand(name = "unban", description = "Unban a user", defaultUserPermissions = [Permission.BAN_MEMBERS, Permission.KICK_MEMBERS, Permission.MODERATE_MEMBERS], guildOnly = true)
    suspend fun unban(ctx: SlashContext, @Description("User ID of user to be unbanned") id: String, @Description("Optional. Why is this user being unbanned?") reason: String?) {
        newSuspendedTransaction {
            Infraction.new {
                this.guildId = ctx.guild!!.idLong
                this.userId = id.toLong()
                this.type = InfractionType.UNBAN
                if (reason != null) { // this feels jank but oh well
                    this.reason = reason
                } else {
                    this.reason = "No reason given"
                }
                this.moderator = ctx.author.idLong
                this.messages = emptyList()
                this.roles = emptyList()
                this.createdAt = System.currentTimeMillis()
                this.duration = 0
            }
        }

        ctx.guild!!.unban(UserSnowflake.fromId(id)).reason(reason).queue()

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "The Ban Hammer has been revoked!"
                color = EmbedHelpers.moderationColor()
                field("Unbanned user ${id}", UserSnowflake.fromId(id).asMention, true)
                field("Reason", reason ?: "No reason given", true)
            }).queue()
    }
}