package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.entities.UserSnowflake
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.util.concurrent.TimeUnit

class Hackban : Scaffold {
    @SlashCommand(name = "hackban", description = "Ban someone using ID, either as a precaution or they have left before you can ban")
    suspend fun hackban(ctx: SlashContext, @Description("User ID of the user to be banned") id: String, @Description("Optional. Why is this user being banned?") reason: String?) {
        newSuspendedTransaction {
            Infraction.new {
                this.guildId = ctx.guild!!.idLong
                this.userId = id.toLong()
                this.type = InfractionType.BAN
                this.reason = reason ?: "No reason given"
                this.moderator = ctx.author.idLong
                this.messages = emptyList()
                this.roles = emptyList()
                this.createdAt = System.currentTimeMillis()
                this.duration = 0
            }
        }

        ctx.guild!!.ban(UserSnowflake.fromId(id), 0 , TimeUnit.SECONDS).reason(reason).queue()

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "The Ban Hammer has been swung!"
                color = EmbedHelpers.moderationColor()
                field("User", UserSnowflake.fromId(id).asMention, true)
                field("User ID", "`${UserSnowflake.fromId(id).id}`", true )
                field("Reason", reason ?: "No reason provided.", true)
            }).queue()
    }
}