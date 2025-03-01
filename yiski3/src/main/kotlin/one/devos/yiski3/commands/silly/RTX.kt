package one.devos.yiski3.commands.silly

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski.common.utils.EmbedHelpers.videoUpload
import one.devos.yiski.common.utils.PathsHelper
import one.devos.yiski3.Yiski3
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

@YiskiModule
class RTX : Scaffold {
    @SlashCommand(name = "rtx", description = "Its just like real life!")
    suspend fun rtx(ctx: SlashContext) {
        val rtxVideo = Yiski3.config.videos.rtx

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "RTX"
                description = "warning: loud"
                color = EmbedHelpers.infoColor()
            }).await()
        ctx.channel.sendFiles(videoUpload(rtxVideo)).await()
    }
}