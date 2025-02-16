package one.devos.yiski.runner.data

import kotlinx.serialization.Serializable

@Serializable
data class GuildResponse(
    val name: String,
    val iconUrl: String,
    val ownerUsername: String,
    val memberCount: Int,
    val verificationLevel: String,
    val emojiCount: Int,
    val stickerCount: Int,
    val boostCount: Int,
)
