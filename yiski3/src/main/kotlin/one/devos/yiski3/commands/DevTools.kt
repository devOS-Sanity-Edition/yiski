package one.devos.yiski3.commands

import com.akuleshov7.ktoml.file.TomlFileReader
import kotlinx.serialization.serializer
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.ConfigSetupEntrypoint
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

        ctx.sendEmbed {
            setTitle(devToolsToml.title.embed)
            setDescription(devToolsToml.text.embed)
            addField(devToolsToml.title.windows, devToolsToml.text.windows, false)
            addField(devToolsToml.title.macOS, devToolsToml.text.macOS, false)
            addField(devToolsToml.title.linux, devToolsToml.text.linux, false)
            addField(devToolsToml.title.result, devToolsToml.text.result, false)
            addField(devToolsToml.title.source, devToolsToml.text.source, false)
        }

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