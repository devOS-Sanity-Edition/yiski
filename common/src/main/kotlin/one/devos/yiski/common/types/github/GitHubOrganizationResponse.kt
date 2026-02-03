package one.devos.yiski.common.types.github

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubOrganizationResponse(
    val login: String,
    val id: Long,
    @SerialName("avatar_url")
    val avatarUrl: String,
    @SerialName("url")
    val apiURL: String,
    @SerialName("html_url")
    val url: String
)
