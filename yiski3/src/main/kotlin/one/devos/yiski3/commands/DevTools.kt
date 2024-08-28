package one.devos.yiski3.commands

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import kotlinx.serialization.serializer
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski.common.utils.EmbedHelpers.imageUpload
import one.devos.yiski.utils.TomlReader
import one.devos.yiski3.Yiski3
import one.devos.yiski3.data.DevToolsData
import one.devos.yiski3.logger
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.nio.charset.StandardCharsets

@YiskiModule
class DevTools : Scaffold {
    @SlashCommand(name = "devtools", description = "How to turn on Discord Dev Tools on stable branch")
    suspend fun devtools(ctx: SlashContext) {
        val devToolsToml = DevToolsToml.read()
        val devToolsImage = Yiski3.config.images.inlineStaticImagesTables.devtools

        ctx.interaction.deferReply()
            .setFiles(imageUpload(devToolsImage))
            .setEmbeds(Embed {
                title = devToolsToml.title.embed
                description = devToolsToml.text.embed
                color = EmbedHelpers.infoColor()
                field(devToolsToml.title.windows, devToolsToml.text.windows, false)
                field(devToolsToml.title.macOS, devToolsToml.text.macOS, false)
                field(devToolsToml.title.linux, devToolsToml.text.linux, false)
                field(devToolsToml.title.result, devToolsToml.text.result, false)
                field(devToolsToml.title.source, devToolsToml.text.source, false)
                image = "attachment://${devToolsImage}"
            })
            .await()

    }
}

@YiskiModule
object DevToolsToml {
    private val devToolsTomlPath = Yiski3::class.java.getResourceAsStream("/assets/devtools.toml")?.bufferedReader(StandardCharsets.UTF_8)!!.readText()

    fun read() : DevToolsData {
        return try {
            logger.info { "Attempting to load the Devtools TOML..." }
            TomlReader.decodeFromString(serializer(), devToolsTomlPath)
        } catch (e: Exception) {
            logger.error {
                "Failed to load the Devtools TOML. Uh oh."
                e
            }
            throw e
        }
    }
}