package one.devos.yiski3.commands.silly

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski.common.utils.EmbedHelpers.imageUpload
import one.devos.yiski.common.utils.PathsHelper
import one.devos.yiski3.Yiski3
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

@YiskiModule
class Teehee : Scaffold {
    @SlashCommand(name = "teehee", description = "got a little silly")
    suspend fun teehee(ctx: SlashContext) {
        val file = Yiski3.config.images.inlineStaticImagesTables.teehee

        ctx.interaction.deferReply()
            .setFiles(imageUpload(file))
            .setEmbeds(Embed {
                title = "teehee"
                description = "*inkling sounds*"
                image = "attachment://${file}"
                color = EmbedHelpers.infoColor()
            })
            .await()
    }
}