package one.devos.yiski.common.types.github

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoryResponse(
    val id: Long,
    val name: String,
    @SerialName("full_name")
    val fullName: String,
    @SerialName("html_url")
    val url: String,
    val description: String?,
    val homepage: String?,
    val organization: GitHubOrganizationResponse,
    @SerialName("open_issues")
    val openIssues: Int
)