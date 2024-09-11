package one.devos.yiski3.commands.silly.headcrab

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski.common.utils.EmbedHelpers.imageUpload
import one.devos.yiski.common.utils.PathsHelper
import one.devos.yiski3.Yiski3
import one.devos.yiski3.data.HeadcrabSuccess
import one.devos.yiski3.logger
import one.devos.yiski3.tables.Headcrab
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

@YiskiModule
class Headcrab : Scaffold {
    @SlashCommand("headcrab", "CRAB YOUR FRIENDS 🦀🦀🦀🦀🦀")
    suspend fun headcrab(ctx: SlashContext, @Description("Who you wanna 🦀!") user: User, @Description("Why are you murdering them?") reason: String?) {
        val authorID = ctx.author.id
        fun reasonAsNull(): String {
            if (reason == null) {
                return ""
            } else {
                return reason
            }
        }

        if (user == ctx.author) {
            newSuspendedTransaction {
                Headcrab.new {
                    this.discordId = user.idLong
                    this.incident = System.currentTimeMillis()
                    this.selfAttempted = true
                    this.successType = HeadcrabSuccess.FUCKING_WHAT
                    if (reason != null) {
                        this.reason = reason
                    } else {
                        this.reason = "Self attempted pain."
                    }
                }
            }

            ctx.sendEmbed {
                setTitle("Self headcrabbing yourself is not allowed here... or anywhere... ever!")
                setColor(EmbedHelpers.failColor())
            }
        }
        val result = (1..100).random()

        if (result >= 91) {
            logger.info { "HEADCRAB FAIL: CHANCE $result" }

            newSuspendedTransaction {
                Headcrab.new {
                    this.discordId = user.idLong
                    this.incident = System.currentTimeMillis()
                    this.selfAttempted = false
                    this.successType = HeadcrabSuccess.FAIL
                    if (reason != null) {
                        this.reason = reason
                    } else {
                        this.reason = ""
                    }
                }
            }

            val inputStreamHeadcrabSuccess = PathsHelper.filePath.file(Yiski3.config.images.inlineGifImageTables.headcrabsuccess)

            ctx.interaction.deferReply()
                .setFiles(imageUpload(Yiski3.config.images.inlineGifImageTables.headcrabfail))
                .setEmbeds(Embed {
                    title = "Headcrab failed!"
                    description = "${user.asMention} has successfully deflected the headcrab from ${ctx.author.asMention}!"
                    image = "attachment://${Yiski3.config.images.inlineGifImageTables.headcrabsuccess}" // Aubree is being a massive HIMBO
                    color = EmbedHelpers.infoColor()
                    if (reason != null) {
                        field("", reasonAsNull(), false)
                    }
                })
                .await()
        } else {
            newSuspendedTransaction {
                Headcrab.new {
                    this.discordId = user.idLong
                    this.incident = System.currentTimeMillis()
                    this.selfAttempted = false
                    this.successType = HeadcrabSuccess.SUCCESS
                    if (reason != null) {
                        this.reason = reason
                    } else {
                        this.reason = ""
                    }
                }
            }

            ctx.sendEmbed {
                setTitle("Headcrab succeeded!")
                setDescription("${user.asMention} has been headcrabbed by ${ctx.author.asMention}!")
                setColor(EmbedHelpers.successColor())
                if (reason != null) {
                    addField("", reasonAsNull(), false)
                }
                logger.info { "HEADCRAB SUCCESS: CHANCE $result" }
            }
        }
    }

}