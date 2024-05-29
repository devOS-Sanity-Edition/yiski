package one.devos.yiski.commands

import net.dv8tion.jda.api.entities.User
import one.devos.yiski.Yiski
import one.devos.yiski.data.YiskiBotConfig
import one.devos.yiski.logger
import one.devos.yiski.utils.Colors
import one.devos.yiski.utils.HexToRGB
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.awt.Color
import java.io.File
import java.nio.file.Paths

class Headcrab : Scaffold{
    @SlashCommand("headcrab", "CRAB YOUR FRIENDS 🦀🦀🦀🦀🦀")
    suspend fun headcrab(ctx: SlashContext, @Description("Who you wanna 🦀!") user: User) {
        if (user == ctx.author) {
            ctx.sendEmbed {
                setTitle("No suicide in my christan discord server!")
            }
            // Add 1 to the suicide counter
        }
        val result = (1..100).random()

        if (result < 101) {
            ctx.sendEmbed {
                setTitle("Headcrab failed!")
                setDescription("${user.asMention} has succsefully deflected the headcrab from ${ctx.author.asMention}!")

                // this set image doesnt work atm + doesnt upload the image, waiting for storm
                setImage("attachment://${Paths.get("").toAbsolutePath().toString()}/${Yiski.config.images.inlineGifImageTables.headcrabfail}") // Aubree is being a massive HIMBO
                setColor(Color(Colors.FAIL.r, Colors.FAIL.g, Colors.FAIL.b))
                logger.info { "HEADCRAB FAIL: CHANCE $result" }
                logger.info { "${Paths.get("").toAbsolutePath().toString()}/${Yiski.config.images.inlineGifImageTables.headcrabfail}" } // testing a theory
                build()
            }
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