package one.devos.basicmodule

import kotlinx.serialization.serializer
import kotlin.system.exitProcess
import one.devos.yiski.utils.TomlReader

object BasicModuleConfig {
    private val configPath: String = System.getProperty("basicmodule_config", "basicmodule.config.toml")

    fun loadConfig(): BasicModuleConfigData {
        logger.info{ "Loading config from $configPath..." }
        return try {
            TomlReader.decodeFromFile(serializer(), configPath)
        } catch (e: Exception) {
            logger.error(e) { "Failed to load config" }
            exitProcess(1)
        }
    }
}