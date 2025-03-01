package one.devos.yiski3.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import one.devos.yiski.common.AbstractYiskiConfig

@Serializable
data class Yiski3ConfigData(
    val images: ImageConfig,
    val videos: VideosConfig,
    val apiKeys: ApiKeysConfig
) : AbstractYiskiConfig {
    @Serializable
    data class ImageConfig(
        @SerialName("static")
        val inlineStaticImagesTables: StaticImageTables,

        @SerialName("gif")
        val inlineGifImageTables: GifImageTables
    )


    @Serializable
    data class VideosConfig(
        val antihorny: String,
        val gasp: String,
        val memoryleak: String,
        val rtx: String,
        val whatareyoudoing: String,
        val osufailscreen: String
    )

    @Serializable
    data class ApiKeysConfig(
        val osu: String
    )
}

@Serializable
data class StaticImageTables(
    val devtools: String,
    val ims: String,
    val lbrf: String,
    val token: String,
    val teehee: String,
    val treeeater: String
)

@Serializable
data class GifImageTables(
    val headcrabsuccess: String,
    val headcrabfail: String,
)