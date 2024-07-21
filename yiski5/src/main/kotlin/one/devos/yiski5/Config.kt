package one.devos.yiski5

import com.akuleshov7.ktoml.file.TomlFileReader
import kotlinx.serialization.serializer
import kotlin.system.exitProcess

object Config {
    private val configPath: String = System.getProperty("yiski5_config", "yiski5_config.toml")

    fun loadConfig(): YiskiConfig {
        logger.info{ "Loading config from $configPath..." }
        return try {
            TomlFileReader.decodeFromFile(serializer(), configPath)
        } catch (e: Exception) {
            logger.error(e) { "Failed to load config" }
            exitProcess(1)
        }
    }
}