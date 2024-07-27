package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.User
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.util.*
import java.util.concurrent.TimeUnit

class Ban : Scaffold {
    @SlashCommand(name = "ban", description = "Ban a user", defaultUserPermissions = [Permission.BAN_MEMBERS, Permission.KICK_MEMBERS, Permission.MODERATE_MEMBERS])
    suspend fun ban(ctx: SlashContext, @Description("Which user?") user: User, @Description("What's the reasoning?") reason: String, @Description("Enter the amount of time you want to wipe a person's messages. [ex: 7d, 5h, 1m, or 0m]") timeframe: String) {
        val banTimeFrameConversion: Map<Char, TimeUnit> = mapOf(
            's' to TimeUnit.SECONDS,
            'm' to TimeUnit.MINUTES,
            'h' to TimeUnit.HOURS,
            'd' to TimeUnit.DAYS,
        )

//        ctx.guild?.ban(user, banTimeFrame.toString()[0].code, banTimeFrameConversion[banTimeFrame[1]])?.reason(reason)?.queue()
        ctx.guild?.ban(user, timeframe[0].code, TimeUnit.HOURS)?.reason(reason)?.queue() // fallback to hours for now until the above is sorted

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "The Ban Hammer has been swung!"
                field("User", user.name, false)
                field("Reason", reason, false)
                footer("CURRENTLY, THIS HAS NOT BEEN LOGGED TO A DATABASE, ONLY TO THE SERVER'S OWN BAN LIST, PLEASE IMPLEMENT SOON")
            }).queue()
    }
}