package one.devos.yiski.common

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.serializer
import one.devos.yiski.common.data.YiskiBotConfig
import one.devos.yiski.utils.TomlReader
import kotlin.system.exitProcess

object Config {
    private val logger = KotlinLogging.logger { }

    private val configPath = System.getProperty("yiski_config", "config.toml")

    fun loadConfig(): YiskiBotConfig {
        return try {
            logger.info { "Attemping to load config" }
            TomlReader.decodeFromFile(serializer(), configPath)

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