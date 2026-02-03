package one.devos.yiski.common

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.*
import one.devos.yiski.common.types.github.GitHubRepositoryResponse

object GitHub {
    private val client = HttpClient(CIO) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }


    suspend fun getRepository(): GitHubRepositoryResponse {
        val response = client.get("https://api.github.com/repos/devOS-Sanity-Edition/yiski")

        return response.body<GitHubRepositoryResponse>()
    }
}