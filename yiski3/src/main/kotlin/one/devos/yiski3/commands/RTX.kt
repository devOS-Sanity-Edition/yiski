package one.devos.yiski3.commands

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.YiskiConstants
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski.common.utils.getConfigSetupEntrypoint
import one.devos.yiski3.Yiski3
import one.devos.yiski3.data.Yiski3ConfigData
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

class RTX : Scaffold {
    // We need to make it so that it can be sent as a video, i.e. figure out a way
    // to send it as a link (local webserver?)

    @SlashCommand(name = "rtx", description = "Its just like real life!")
    suspend fun rtx(ctx: SlashContext) {
        val rtxVideo = EmbedHelpers.imagesPath(Yiski3.config.videos.rtx)

        ctx.interaction.deferReply()
            .setFiles(FileUpload.fromData(rtxVideo, Yiski3.config.videos.rtxfile))
            .setEmbeds(Embed {
                title = "RTX"
                image ="attachment://${Yiski3.config.videos.rtxfile}"
                color = EmbedHelpers.infoColor()
            })
            .await()
    }

}