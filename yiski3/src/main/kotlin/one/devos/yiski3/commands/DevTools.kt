package one.devos.yiski3.commands

import com.akuleshov7.ktoml.file.TomlFileReader
import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import kotlinx.serialization.serializer
import net.dv8tion.jda.api.utils.FileUpload
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.ConfigSetupEntrypoint
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski3.Yiski3
import one.devos.yiski3.data.DevToolsData
import one.devos.yiski3.logger
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import kotlin.system.exitProcess

class DevTools : Scaffold {
    @OptIn(YiskiModule::class)
    @SlashCommand(name = "devtools", description = "How to turn on Discord Dev Tools on stable branch")
    suspend fun devtools(ctx: SlashContext) {
        val devToolsToml = DevToolsToml.read()
        val devToolsImage = EmbedHelpers.imagesPath(Yiski3.config.images.inlineStaticImagesTables.devtools)

        ctx.interaction.deferReply()
            .setFiles(FileUpload.fromData(devToolsImage, Yiski3.config.images.inlineStaticImagesTables.devtoolsfile))
            .setEmbeds(Embed {
                title = devToolsToml.title.embed
                description = devToolsToml.text.embed
                color = EmbedHelpers.infoColor()
                field(devToolsToml.title.windows, devToolsToml.text.windows, false)
                field(devToolsToml.title.macOS, devToolsToml.text.macOS, false)
                field(devToolsToml.title.linux, devToolsToml.text.linux, false)
                field(devToolsToml.title.result, devToolsToml.text.result, false)
                field(devToolsToml.title.source, devToolsToml.text.source, false)
                image = "attachment://${Yiski3.config.images.inlineStaticImagesTables.devtoolsfile}"
            })
            .await()

    }
}

@YiskiModule
object DevToolsToml {
    private val devToolsTomlPath = System.getProperty("devtools", "assets/devtools.toml")

    fun read() : DevToolsData {
        return try {
            logger.info { "Attempting to load the Devtools TOML..." }
            TomlFileReader.decodeFromFile(serializer(), devToolsTomlPath)
        } catch (e: Exception) {
            logger.error {
                "Failed to load the Devtools TOML. Uh oh."
                e
            }
            throw e
        }
    }
}