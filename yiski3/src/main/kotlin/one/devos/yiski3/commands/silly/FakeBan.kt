package one.devos.yiski3.commands.silly

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import dev.minn.jda.ktx.messages.MessageEdit
import net.dv8tion.jda.api.entities.User
import one.devos.yiski.common.utils.RandomTextList
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class FakeBan : Scaffold {

    @SlashCommand(name = "fakeban", description = "Ban a user*")
    suspend fun fakeban(ctx: SlashContext, @Description("Which user?") user: User, @Description("What's the reasoning?") reason: String) {
        val godForgiveMeForIHaveSinnedWithThePlant = (1..1000).random()

        ctx.interaction.deferReply()
            .setContent("Hey ${user.asMention}")
            .setEmbeds(Embed {
                title = "The Ban Hammer has been swung!*"
                field("User", user.name, false)
                field("Reason", reason, false)
                field("", "-# ${RandomTextList.sillyFakeBanFooters()}")
                if (godForgiveMeForIHaveSinnedWithThePlant >= 999) {
                    image = "https://tenor.com/view/peashooter-pvz-plants-vs-zombies-plants-zombies-gif-6998445874810870689" // i am so fuckin sorry (not, skill issue)
                }
            }).await()
    }

}