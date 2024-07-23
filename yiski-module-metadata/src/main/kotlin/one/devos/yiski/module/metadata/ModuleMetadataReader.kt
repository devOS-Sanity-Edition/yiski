package one.devos.yiski.module.metadata

import com.akuleshov7.ktoml.file.TomlFileReader
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.serializer
import kotlin.system.exitProcess

object ModuleMetadataReader {

    private val logger = KotlinLogging.logger {  }

    fun read(content: String): ModuleMetadata {
        return try {
            TomlFileReader.decodeFromString(serializer(), content)
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
