package one.devos.yiski.module.metadata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ModuleMetadata(
    val metadata: MetadataConfig,
    val runner: RunnerConfig,
    val module: ModuleConfig,
    val information: InformationConfig
) {

    @Serializable
    data class MetadataConfig(
        val version: Byte
    )

    @Serializable
    data class RunnerConfig(
        val mainClass: String
    )

    @Serializable
    data class ModuleConfig(
        val configClass: String
    ) {

        @SerialName("packages")
        val packages: PackagesConfig? = null

        @Serializable
        data class PackagesConfig(
            val databasePackage: String,
            val slashCommandsPackage: String
        )

    }

    @Serializable
    data class InformationConfig(
        val name: String,
        val description: String,
        val version: String,
        val repo: String,
        val license: String,
        val authors: List<String>
    )

    companion object {

        const val FILE_NAME = "yiski.metadata.toml"

    }

}
