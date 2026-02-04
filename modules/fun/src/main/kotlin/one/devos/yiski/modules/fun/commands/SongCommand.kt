package one.devos.yiski.modules.`fun`.commands

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.network.parseGetRequest
import com.fleeksoft.ksoup.select.Evaluator
import dev.kord.common.entity.DiscordPartialEmoji
import dev.kord.common.entity.MessageFlag
import dev.kord.common.entity.SeparatorSpacingSize
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.rest.builder.component.actionRow
import dev.kord.rest.builder.component.section
import dev.kord.rest.builder.component.separator
import dev.kord.rest.builder.message.container
import dev.kord.rest.builder.message.messageFlags
import dev.lizainslie.moeka.core.commands.argument.ArgumentTypes
import dev.lizainslie.moeka.core.commands.defineCommand
import dev.lizainslie.moeka.platforms.discord.Discord
import dev.lizainslie.moeka.platforms.discord.commands.DiscordCommandContext
import dev.lizainslie.moeka.platforms.discord.commands.enforceDiscordSlash
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.appendPathSegments
import io.ktor.http.encodeURLParameter
import io.ktor.http.headers
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import one.devos.yiski.common.Http
import one.devos.yiski.common.KotlinxGenericMapSerializer
import one.devos.yiski.common.KotlinxGenericMapSerializer.toJsonElement
import java.time.ZoneId
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

val SongCommand = defineCommand(
    name = "song",
    description = "Look up a song and get a link for other plaforms"
) {
    platform(Discord)

    val urlArg = argument("link", "The link of the song you want to look up from your platform of choice.", ArgumentTypes.STRING)

    val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36"

    @Serializable
    data class SongDotLinkResponse(
        val provider: String,
        val type: String,
        val id: String,
    )

    handle {
        if (this !is DiscordCommandContext) return@handle
        val songUrl by urlArg.require("I need a song link silly.")

        enforceDiscordSlash {
            val deferredReply = interaction.deferPublicResponse()

            val resolveSong = try {
                Http.client.get("https://api.odesli.co/resolve") {
                    expectSuccess = true
                    headers {
                        append(HttpHeaders.UserAgent, userAgent)
                    }
                    url {
                        parameters.append("url", songUrl.encodeURLParameter())
                    }
                }
            } catch (e: ClientRequestException) {
                deferredReply.respond {
                    content = "Oops, something went wrong."
                }
                return@enforceDiscordSlash
            }

            val resolveSongBody = resolveSong.body<SongDotLinkResponse>()

            val providerCode = when(val p = resolveSongBody.provider) {
                "soundcloud" -> "sc"
                "audiomack" -> "am"
                else -> p.first().toString()
            }

            val doc = Ksoup.parseGetRequest("https://${resolveSongBody.type}.link", httpClient = Http.client) {
                expectSuccess = true
                headers {
                    append(HttpHeaders.UserAgent, userAgent)
                }
                url {
                    appendPathSegments(providerCode, resolveSongBody.id)
                }
            }

            try {
                val nextData = doc.select(Evaluator.Id("__NEXT_DATA__")).firstOrNull() ?: error("could not get the data from the page")

                val data = Json.decodeFromString(KotlinxGenericMapSerializer, nextData.data())

                val pageData = data["props"]
                    .toJsonElement()
                    .jsonObject["pageProps"]!!
                    .jsonObject["pageData"]!!
                    .jsonObject

                val links = pageData["sections"]!!
                    .jsonArray[1]
                    .jsonObject["links"]!!
                    .jsonArray

                val songData = pageData["entityData"]!!
                    .jsonObject

                val releaseEpoch = songData["releaseDate"]?.jsonObject.let {
                    Calendar.Builder()
                        .setDate(
                            it?.get("year")?.jsonPrimitive?.content?.toInt() ?: 0,
                            it?.get("month")?.jsonPrimitive?.content?.toInt() ?: 0,
                            it?.get("day")?.jsonPrimitive?.content?.toInt() ?: 0
                        )
                        .setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")))
                        .build()
                        .toInstant().epochSecond
                }

                deferredReply.respond {
                    messageFlags {
                        +MessageFlag.IsComponentsV2
                    }

                    container {
                        section {
                            if (songData["thumbnailUrl"] != null) thumbnailAccessory { url = songData["thumbnailUrl"]!!.jsonPrimitive.content }
                            textDisplay { content = "# ${songData["title"]?.jsonPrimitive?.content ?: "Unknown Title"}" }
                            textDisplay { content = songData["artistName"]?.jsonPrimitive?.content ?: "Unknown Artist" }
                            textDisplay { content = "-# ${songData["genre"]?.jsonPrimitive?.content ?: "Unknown Genre"} · <t:${releaseEpoch}:D>" }
                        }
                        actionRow {
                            val type = (songData["type"]?.jsonPrimitive?.content ?: "Unknown Type").replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            }
                            linkButton(pageData["pageUrl"]?.jsonPrimitive?.content ?: "https://song.link") {
                                label = if (type == "Album") "${songData["numTracks"]?.jsonPrimitive?.content ?: 0} tracks" else "song.link"
                                emoji = DiscordPartialEmoji(name = "\uD83C\uDFB6")
                            }
                        }
                        separator { divider = true; spacing = SeparatorSpacingSize.Small }
                        links.map { it.jsonObject }.filter { it["url"] != null }.chunked(4).forEach { group ->
                            actionRow {
                                group.forEach { provider ->
                                    linkButton(provider["url"]!!.jsonPrimitive.content) {
                                        label = provider["displayName"]!!.jsonPrimitive.content
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                return@enforceDiscordSlash
            }
        }
    }
}