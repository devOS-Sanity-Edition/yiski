package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.User
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class Kick : Scaffold {
    @SlashCommand(name = "kick", description = "Kicks a user", defaultUserPermissions = [Permission.BAN_MEMBERS, Permission.KICK_MEMBERS, Permission.MODERATE_MEMBERS])
    suspend fun kick(ctx: SlashContext, @Description("Which user?") user: User, @Description("What's the reason?") reason: String) {
        ctx.guild?.kick(user)?.reason(reason)?.queue()

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "The Boot has kicked someone!"
                field("User", user.name, false)
                field("Reason", reason, false)
                footer("CURRENTLY, THIS HAS NOT BEEN LOGGED TO A DATABASE, ONLY TO THE SERVER'S OWN AUDIT LOG, PLEASE IMPLEMENT SOON")
            }).queue()
    }
}