package one.devos.yiski5

import com.akuleshov7.ktoml.file.TomlFileReader
import kotlinx.serialization.serializer
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.ConfigSetupEntrypoint
import one.devos.yiski5.data.Yiski5ConfigData
import kotlin.system.exitProcess

@OptIn(YiskiModule::class)
object Yiski5Config : ConfigSetupEntrypoint {
    private val configPath: String = System.getProperty("yiski5_config", "yiski5_config.toml")

    override fun load(): Yiski5ConfigData {
        logger.info{ "Loading config from $configPath..." }
        return try {
            TomlFileReader.decodeFromFile(serializer(), configPath)
        } catch (e: Exception) {
            logger.error(e) { "Failed to load config" }
            exitProcess(1)
        }

    }
}