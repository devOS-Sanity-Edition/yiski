package one.devos.yiski3

import kotlinx.serialization.serializer
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.ConfigSetupEntrypoint
import one.devos.yiski.utils.TomlReader
import one.devos.yiski3.data.Yiski3ConfigData
import kotlin.system.exitProcess

@YiskiModule
class Yiski3Config : ConfigSetupEntrypoint {

    private val configPath = System.getProperty("yiski3_config", "yiski3.config.toml")

    override lateinit var config: Yiski3ConfigData

    override fun read() {
        config = try {
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
