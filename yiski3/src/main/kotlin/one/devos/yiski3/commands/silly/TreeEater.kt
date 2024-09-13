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
class TreeEater : Scaffold {
    @SlashCommand(name = "treeeater", description = "yes")
    suspend fun treeeater(ctx: SlashContext) {
        val treeEaterImage = Yiski3.config.images.inlineStaticImagesTables.treeeater

        ctx.interaction.deferReply()
            .setFiles(imageUpload(treeEaterImage))
            .setEmbeds(Embed {
                title = "Tree Eater"
                description = "it's exactly what it sounds like"
                image = "attachment://${treeEaterImage}"
                color = EmbedHelpers.infoColor()
            })
            .await()
    }
}