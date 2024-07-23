package one.devos.yiski1.tables.moderation

import kotlinx.serialization.Serializable

@Serializable
data class InfractionMessage(
    val authorId: Long,
    val authorTag: String,
    val messageId: Long,
    val message: String,
    val timestamp: Long,
)
