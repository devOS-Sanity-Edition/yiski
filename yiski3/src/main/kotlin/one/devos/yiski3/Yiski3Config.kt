package one.devos.yiski3

import com.akuleshov7.ktoml.file.TomlFileReader
import kotlinx.serialization.serializer
import one.devos.yiski3.data.Yiski3ConfigData
import kotlin.system.exitProcess

object Yiski3Config {
    private val configPath = System.getProperty("yiski1_config", "yiski1_config.toml")

    fun loadConfig(): Yiski3ConfigData {
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