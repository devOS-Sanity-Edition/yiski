package one.devos.yiski3.data

import kotlinx.serialization.Serializable
import one.devos.yiski.common.AbstractYiskiConfig

@Serializable
data class DevToolsData(
    val title: TitlePortion,
    val text: TextPortion
) {
    @Serializable
    data class TitlePortion(
        val embed: String,
        val windows: String,
        val linux: String,
        val macOS: String,
        val result: String,
        val source: String
    )

    @Serializable
    data class TextPortion(
        val embed: String,
        val windows: String,
        val linux: String,
        val macOS: String,
        val result: String,
        val source: String
    )
}
