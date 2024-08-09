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
class MemoryLeak : Scaffold {
    @SlashCommand(name = "memoryleak", description = "signal 11 (SIGSEGV), code 1 (SEGV_MAPERR)")
    suspend fun memoryleak(ctx: SlashContext) {
        val memoryLeak = Yiski3.config.videos.memoryleak

        ctx.interaction.deferReply()
            .setEmbeds(Embed {
                title = "`signal 11 (SIGSEGV), code 1 (SEGV_MAPERR)`"
                description = "Buffer overflow"
                color = EmbedHelpers.infoColor()
            }).await()
        ctx.channel.sendFiles(videoUpload(memoryLeak)).await()
    }
}