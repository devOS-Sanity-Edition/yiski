package one.devos.yiski.common.data

import kotlinx.serialization.Serializable

@Serializable
data class YiskiBotConfig(
    val universal: UniversalConfig,
    val discord: DiscordConfig,
) {
    @Serializable
    data class UniversalConfig(
        val githubToken: String
    )

    @Serializable
    data class DiscordConfig(
        val botToken: String,
        val adminIDs: Set<Long>,
        val homeGuildID: Long
    )
}

// Structure:
//
// [universal]
// githubToken = "github api token"
//
// [discord]
// botToken = "discord bot token"
// adminIDs = [ 0000000000000000000, 0000000000000000001 ]
// homeGuildID = 0000000000000000000sd
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