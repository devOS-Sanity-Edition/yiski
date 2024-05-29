package one.devos.yiski.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class YiskiBotConfig(
    val universal: UniversalConfig,
    val discord: DiscordConfig,
    val images: ImagesConfig,
    val videos: VideosConfig
) {
    @Serializable
    data class UniversalConfig(
        val githubToken: String
    )

    @Serializable
    data class DiscordConfig(
        val botToken: String,
        val adminIDs: Set<Long>
    )
//
    @Serializable
    data class ImagesConfig(
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
        val whatareyoudoing: String
    )
}

@Serializable
data class StaticImageTables(
    val devtools: String,
    val ims: String,
    val token: String
)

@Serializable
data class GifImageTables(
    val headcrabsuccess: String,
    val headcrabfail: String,
)

// Structure:
//
// [universal]
// githubToken = "github api token"
//
// [discord]
// botToken = "discord bot token"
// adminIDs = [ 0000000000000000000, 0000000000000000001 ]
//
// [images]
//     [images.static]
//     devtools = "assets/images/devtoolsconfig.png"
//     ims = "assets/images/dreadfulims.png"
//     token = "assets/images/token.png"
//
// [videos]
//     antihorny = "assets/videos/antihorny.mp4"
//     gasp = "assets/videos/gasp.mp4"
//     memoryleak = "assets/videos/memoryleak.mp4"
//     rtx = "assets/videos/rtx.mp4"
//     whatareyoudoing = "assets/videos/whatareyoudoing.mp4"