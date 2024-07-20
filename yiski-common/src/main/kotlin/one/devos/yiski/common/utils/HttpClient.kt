package one.devos.yiski.common.utils

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*

object HttpClient {
    val client = HttpClient(CIO) { install(DefaultRequest) }
}