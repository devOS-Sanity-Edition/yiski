package one.devos.yiski5

import com.akuleshov7.ktoml.file.TomlFileReader
import kotlinx.serialization.serializer
import one.devos.yiski5.data.YiskiConfigData
import kotlin.system.exitProcess

object Yiski5Config {
    private val configPath: String = System.getProperty("yiski5_config", "yiski5_config.toml")

    fun loadConfig(): YiskiConfigData {
        logger.info{ "Loading config from $configPath..." }
        return try {
            TomlFileReader.decodeFromFile(serializer(), configPath)
        } catch (e: Exception) {
            logger.error(e) { "Failed to load config" }
            exitProcess(1)
        }
    }
}