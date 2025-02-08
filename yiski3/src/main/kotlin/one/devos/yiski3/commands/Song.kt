package one.devos.yiski3.commands

import com.fleeksoft.ksoup.Ksoup
import com.fleeksoft.ksoup.nodes.Document
import com.fleeksoft.ksoup.select.Evaluator
import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.interactions.components.link
import dev.minn.jda.ktx.interactions.components.row
import dev.minn.jda.ktx.messages.Embed
import dev.minn.jda.ktx.messages.editMessage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski3.utils.KotlinxGenericMapSerializer
import one.devos.yiski3.utils.KotlinxGenericMapSerializer.toJsonElement
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.net.URL
import java.time.ZoneId
import java.util.*

// @TODO Implement rate limits & caching results to the database to avoid API limits.
@YiskiModule
class Song : Scaffold {
    private val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/132.0.0.0 Safari/537.36"

    @Serializable
    data class ResolveStructure(
        val provider: String,
        val type: String,
        val id: String,
    )

    @SlashCommand(name = "song", description = "Look up a song for other platforms")
    suspend fun song(ctx: SlashContext, url: URL) {
        val reply = ctx.interaction.deferReply().await();

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        val resolve = client.get("https://api.odesli.co/resolve") {
            url {
                parameters.append("url", url.toString().encodeURLParameter())
            }
            headers {
                append(HttpHeaders.UserAgent, userAgent)
            }
        }

        val resolveBody = resolve.body<ResolveStructure>()

        val provider = when(val p = resolveBody.provider) {
            "soundcloud" -> "sc"
            "audiomack" -> "am"
            else -> p.first().toString()
        }

        val request = client.get("https://${resolveBody.type}.link") {
            url {
                appendPathSegments(provider, resolveBody.id)
            }
            headers {
                append(HttpHeaders.UserAgent, userAgent)
            }
        }

        val doc: Document = Ksoup.parse(html = request.bodyAsText())
        val nextData = doc.select(Evaluator.Id("__NEXT_DATA__")).first() ?: error("could not get the data from the page")

        val data = Json.decodeFromString(KotlinxGenericMapSerializer, nextData.data())
        val pageData = data["props"]
            .toJsonElement()
            .jsonObject["pageProps"]!!
            .jsonObject["pageData"]!!
            .jsonObject

        val sections = pageData["sections"]!!
            .jsonArray

        val songData = pageData["entityData"]!!
            .jsonObject

        val links = sections[1].jsonObject["links"]!!.jsonArray

        reply
            .editMessage(
                content = "Here you go, ${ctx.interaction.member!!.asMention}!",
                embeds = listOf(
                    Embed {
                        val type = songData["type"]?.jsonPrimitive?.content?.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }

                        author("${songData["artistName"]!!.jsonPrimitive.content} | $type", pageData["pageUrl"]!!.jsonPrimitive.content)
                        title = songData["title"]!!.jsonPrimitive.content
                        thumbnail = songData["thumbnailUrl"]!!.jsonPrimitive.content
                        if (songData["releaseDate"] !== null) field {
                            val releaseEpoch = songData["releaseDate"]!!.jsonObject.let {
                                Calendar.Builder()
                                    .setDate(
                                        it["year"]!!.jsonPrimitive.content.toInt(),
                                        it["month"]!!.jsonPrimitive.content.toInt(),
                                        it["day"]!!.jsonPrimitive.content.toInt()
                                    )
                                    .setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")))
                                    .build()
                                    .toInstant().epochSecond
                            }
                            name = "Released on"
                            value = "<t:${releaseEpoch}:D>"
                            inline = true
                        }
                        if (songData["genre"] != null) field {
                            name = "Genre"
                            value = songData["genre"]!!.jsonPrimitive.content
                            inline = true
                        }
                        if (type == "Album") field {
                            name = "Tracks"
                            value = songData["numTracks"]!!.jsonPrimitive.content
                            inline = true
                        }
                        footer("Powered by odesli.co")
                    }
                ),
                components = links.map { it.jsonObject }.filter { it["url"] != null }.chunked(5).map { group ->
                    row(
                        *group.map { provider ->
                            link(
                                provider["url"]!!.jsonPrimitive.content,
                                provider["displayName"]!!.jsonPrimitive.content
                            )
                        }.toTypedArray()
                    )
                }
            )
            .await()
    }
}