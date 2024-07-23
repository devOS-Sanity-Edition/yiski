package one.devos.yiski1

import com.akuleshov7.ktoml.file.TomlFileReader
import kotlinx.serialization.serializer
import one.devos.yiski1.data.Yiski1ConfigData
import kotlin.system.exitProcess

object Yiski1Config {
    private val configPath = System.getProperty("yiski1_config", "yiski1_config.toml")

    fun loadConfig(): Yiski1ConfigData {
        return try {
            logger.info { "Attemping to load config" }
            TomlFileReader.decodeFromFile(serializer(), configPath)
        } catch (e: Exception) {
            logger.error {
                """
                    
                    
                    #######################################################
                    #                                                     #
                    #    oopsies woopsies we did a wittle fwucky wucky    #
                    #                                                     #
                    #######################################################
                    
                """.trimIndent()
                e
            }
            exitProcess(1)
        }
    }
}