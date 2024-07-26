package one.devos.yiski3.commands.silly

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski3.Yiski3
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

@YiskiModule
class IMS : Scaffold {
    @SlashCommand(name = "ims", description = "yes")
    suspend fun ims(ctx: SlashContext) {
        val imsImage = EmbedHelpers.imagesPath(Yiski3.config.images.inlineStaticImagesTables.ims)

        ctx.interaction.deferReply()
            .setFiles(FileUpload.fromData(imsImage, Yiski3.config.images.inlineStaticImagesTables.imsfile))
            .setEmbeds(Embed {
                title = "IMS"
                description = "Go fuck yourself"
                image = "attachment://${Yiski3.config.images.inlineStaticImagesTables.imsfile}"
                color = EmbedHelpers.infoColor()
            })
            .await()
    }
}