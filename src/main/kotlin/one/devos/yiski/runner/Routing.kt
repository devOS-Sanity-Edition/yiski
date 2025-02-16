package one.devos.yiski.runner

import dev.minn.jda.ktx.coroutines.await
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.dv8tion.jda.api.entities.Guild
import one.devos.yiski.common.YiskiShared
import one.devos.yiski.runner.data.GuildResponse
import one.devos.yiski.runner.data.InfractionResponse
import one.devos.yiski.runner.data.VersionsResponse
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionsTable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("hai hai?!?1 i guess you found me, oh s-siwwy you?!?1 w-weww don't mind me, im just a bot... existing... bweathing... sighing. teww the *boops your nose* devs *starts twerking* i said hai?!?1 :3")
        }

        get("/version") {
            val versions = VersionsResponse()
            call.respond(versions)
        }

        route("/guild/{id}") {
            get {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val guild = Bootstrapper.jda.getGuildById(id) ?: return@get call.respond(HttpStatusCode.NotFound)

                val data = guild.let {
                    GuildResponse(
                        name = it.name,
                        iconUrl = it.iconUrl ?: "",
                        ownerUsername = it.retrieveOwner().await().user.name,
                        memberCount = it.memberCount,
                        verificationLevel = when (it.verificationLevel) {
                            Guild.VerificationLevel.NONE -> "None"
                            Guild.VerificationLevel.LOW -> "Low"
                            Guild.VerificationLevel.MEDIUM -> "Medium"
                            Guild.VerificationLevel.HIGH -> "High"
                            Guild.VerificationLevel.VERY_HIGH -> "Very High"
                            Guild.VerificationLevel.UNKNOWN -> "Unknown"
                        },
                        emojiCount = it.emojis.size,
                        stickerCount = it.stickers.size,
                        boostCount = it.boostCount
                    )
                }

                call.respond(data)
            }

            get("/logs") {
                val id: Long = call.parameters["id"]?.toLong() ?: return@get call.respond(HttpStatusCode.BadRequest)

                val infractions = newSuspendedTransaction {
                    Infraction
                        .find { (InfractionsTable.guildId eq id) }
                        .map { InfractionResponse(
                            id = it.id.toString(),
                            guildId = it.guildId,
                            userId = it.userId,
                            type = it.type.name.uppercase(),
                            reason = it.reason,
                            moderator = it.moderator,
                            messages = it.messages,
                            roles = it.roles,
                            createdAt = it.createdAt,
                            duration = it.duration,
                            endTime = it.endTime,
                        ) }
                }

                call.respond(infractions)
            }
        }

        get("/modules") {
            val data = YiskiShared.moduleLoader.discover()
            call.respond(data)
        }
    }
}