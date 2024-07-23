package one.devos.basicmodule

import com.akuleshov7.ktoml.file.TomlFileReader
import kotlinx.serialization.serializer
import kotlin.system.exitProcess

object BasicModuleConfig {
    private val configPath: String = System.getProperty("basicmodule_config", "basicmodule_config.toml")

    fun loadConfig(): BasicModuleConfigData {
        logger.info{ "Loading config from $configPath..." }
        return try {
            TomlFileReader.decodeFromFile(serializer(), configPath)
        } catch (e: Exception) {
            logger.error(e) { "Failed to load config" }
            exitProcess(1)
        }
    }
}