package one.devos.yiski3

import com.akuleshov7.ktoml.file.TomlFileReader
import kotlinx.serialization.serializer
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.ConfigSetupEntrypoint
import one.devos.yiski3.data.Yiski3ConfigData
import kotlin.system.exitProcess

@OptIn(YiskiModule::class)
object Yiski3Config : ConfigSetupEntrypoint {
    private val configPath = System.getProperty("yiski3_config", "yiski3_config.toml")

    override fun load() {
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
}