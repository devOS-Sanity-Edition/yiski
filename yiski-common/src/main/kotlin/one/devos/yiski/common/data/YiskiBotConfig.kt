package one.devos.yiski.common.data

import kotlinx.serialization.Serializable

@Serializable
data class YiskiBotConfig(
    val universal: UniversalConfig,
    val discord: DiscordConfig,
    val database: PostgresConfig,

    ) {
    @Serializable
    data class PostgresConfig(
        val host: String,
        val port: Int,
        val username: String,
        val password: String,
        val db: String
    )

    @Serializable
    data class UniversalConfig(
        val githubToken: String
    )

    @Serializable
    data class DiscordConfig(
        val botToken: String,
        val adminIDs: Set<Long>,
        val homeGuildID: Long,
        val activityType: String,
        val activityStatus: String
    )
}