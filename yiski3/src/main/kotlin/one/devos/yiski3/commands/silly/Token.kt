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
class Token : Scaffold {
    @SlashCommand(name = "token", description = "Its always the token")
    suspend fun token(ctx: SlashContext) {
        val imsImage = EmbedHelpers.imagesPath(Yiski3.config.images.inlineStaticImagesTables.token)

        ctx.interaction.deferReply()
            .setFiles(FileUpload.fromData(imsImage, Yiski3.config.images.inlineStaticImagesTables.tokenfile))
            .setEmbeds(Embed {
                title = "Token"
                description = "I love leaking my Token!"
                image = "attachment://${Yiski3.config.images.inlineStaticImagesTables.tokenfile}"
                color = EmbedHelpers.infoColor()
            })
            .await()
    }
}