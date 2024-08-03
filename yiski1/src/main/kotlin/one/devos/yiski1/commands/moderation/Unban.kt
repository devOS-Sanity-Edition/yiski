package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.exceptions.ErrorResponseException
import net.dv8tion.jda.api.requests.ErrorResponse
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionType
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.entities.Scaffold
import xyz.artrinix.aviation.internal.arguments.types.Snowflake

@SlashCommand(
    name = "unban",
    description = "Unban a user",
    defaultUserPermissions = [Permission.BAN_MEMBERS, Permission.KICK_MEMBERS, Permission.MODERATE_MEMBERS],
    guildOnly = true
)
class Unban : Scaffold {
    @SlashSubCommand("Unban a user with their ID")
    suspend fun id(ctx: SlashContext, @Description("User to unban?") id: Snowflake, @Description("Optional. Why is this user being unbanned?") reason: String? = "No reason provided.") {
        val interaction = ctx.interaction.deferReply().await()

        newSuspendedTransaction {
            Infraction.new {
                guildId = ctx.guild!!.idLong
                userId = id.resolved
                type = InfractionType.UNBAN
                this.reason = reason ?: ""
                moderator = ctx.author.idLong
                messages = emptyList()
                roles = emptyList()
                createdAt = System.currentTimeMillis()
                duration = 0
            }
        }

        val user = try {
            val user = ctx.jda.retrieveUserById(id.resolved).await()
            ctx.guild!!.unban(user).reason(reason).await()
            user
        } catch (e: ErrorResponseException) {
            interaction.editOriginalEmbeds(Embed {
                author("I dropped the hammer D:")
                description = when (e.errorResponse) {
                    ErrorResponse.UNKNOWN_BAN -> "<@${id.resolved}> is not currently banned."
                    ErrorResponse.UNKNOWN_USER -> "Discord could not resolve a user for ID `${id}`"
                    else -> "I don't know what the fuck just happened."
                }
                color = EmbedHelpers.failColor()
            }).await()
            return
        }

        interaction
            .editOriginalEmbeds(Embed {
                author("The Ban Hammer has been revoked!", null, ctx.guild!!.iconUrl)
                description = "@${user.name} (${user.id}) has been unbanned."
                color = EmbedHelpers.moderationColor()
                field("Reason", reason ?: "No reason given", true)
                field("Moderator", ctx.author.asMention, true)
            })
            .await()
    }
}