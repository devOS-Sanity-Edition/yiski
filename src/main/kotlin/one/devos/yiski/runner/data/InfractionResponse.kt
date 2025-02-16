package one.devos.yiski.runner.data

import kotlinx.serialization.Serializable

@Serializable
data class InfractionResponse(
    val id: String,
    val guildId: Long,
    val userId: Long,
    val type: String,
    val reason: String,
    val moderator: Long,
    val messages: List<String>,
    val roles: List<Long>,
    val createdAt: Long,
    val duration: Long,
    val endTime: Long,
)
