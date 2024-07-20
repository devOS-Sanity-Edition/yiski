package one.devos.yiski1.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Yiski1ConfigData(
    val images: ImageConfig,
    val videos: VideosConfig
) {
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
        val antihornyfile: String,
        val gasp: String,
        val gaspfile: String,
        val memoryleak: String,
        val memoryleakfile: String,
        val rtx: String,
        val rtxfile: String,
        val whatareyoudoing: String,
        val whatareyoudoingfile: String
    )
}

@Serializable
data class StaticImageTables(
    val devtools: String,
    val devtoolsfile: String,
    val ims: String,
    val imsfile: String,
    val token: String,
    val tokenfile: String
)

@Serializable
data class GifImageTables(
    val headcrabsuccess: String,
    val headcrabsuccessfile: String,
    val headcrabfail: String,
    val headcrabfailfile: String,
)