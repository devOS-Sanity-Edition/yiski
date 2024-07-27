package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.entities.UserSnowflake
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import one.devos.yiski1.tables.moderation.InfractionMessage
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import xyz.artrinix.aviation.internal.arguments.types.Snowflake

class Unban : Scaffold {
    @SlashCommand(name = "unban", description = "Unban a user")
    suspend fun unban(ctx: SlashContext, @Description("User ID of user to be unbanned") userid: String) {
        ctx.guild?.unban(User.fromId(userid))

        ctx.sendEmbed {
            setTitle("The Ban Hammer has been revoked!")
            addField("Unbanned user $userid", User.fromId(userid).asMention, true)
        }
    }
}