package one.devos.yiski3.commands.silly

import dev.minn.jda.ktx.coroutines.await
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers.videoUpload
import one.devos.yiski.common.utils.PathsHelper
import one.devos.yiski3.Yiski3
import one.devos.yiski3.logger
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold

@YiskiModule
class Antihorny : Scaffold {
    @SlashCommand(name = "antihorny", description = "For when you need to pull out the spray bottle")
    suspend fun antihorny(ctx: SlashContext, @Description("Suspect") user: User) {
        val antiHorny = Yiski3.config.videos.antihorny

        logger.debug { videoUpload(antiHorny) }

        ctx.interaction.deferReply()
            .setContent(user.asMention)
            .setFiles(videoUpload(antiHorny)).await()
    }
}