package one.devos.yiski3.commands.headcrab

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.data.Colors
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski3.Yiski3
import one.devos.yiski3.logger
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.awt.Color


class Headcrab : Scaffold {

    @SlashCommand("headcrab", "CRAB YOUR FRIENDS 🦀🦀🦀🦀🦀")
    suspend fun headcrab(ctx: SlashContext, @Description("Who you wanna 🦀!") user: User) {
        val authorID = ctx.author.id


        if (user == ctx.author) {
            ctx.sendEmbed {
                setTitle("No suicide in my christan discord server!")
            }
            // Add 1 to the suicide counter
        }
        val result = (1..100).random()

        if (result >= 91) {
            logger.info { "HEADCRAB FAIL: CHANCE $result" }

            val inputStreamHeadcrabSuccess = EmbedHelpers.imagesPath(Yiski3.instance.config.images.inlineGifImageTables.headcrabsuccess)

            ctx.channel
                .sendFiles(FileUpload.fromData(inputStreamHeadcrabSuccess, Yiski3.instance.config.images.inlineGifImageTables.headcrabfailfile))
                .setEmbeds(Embed {
                    title = "Headcrab failed!"
                    description = "${user.asMention} has succsefully deflected the headcrab from ${ctx.author.asMention}!"
                    image = "attachment://${Yiski3.instance.config.images.inlineGifImageTables.headcrabsuccessfile}" // Aubree is being a massive HIMBO
                    color = EmbedHelpers.infoColor()
                })
                .await()

            // TODO: Postgres tables
        } else {
            ctx.sendEmbed {
                setTitle("Headcrab succeeded!")
                setDescription("${user.asMention} has been headcrabbed by ${ctx.author.asMention}!")
                setColor(Color(Colors.SUCCESS.r, Colors.SUCCESS.g, Colors.SUCCESS.b))
                build()
                logger.info { "HEADCRAB SUCCESS: CHANCE $result" }
            }
        }
    }
}