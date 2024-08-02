package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.interactions.components.sendPaginator
import dev.minn.jda.ktx.messages.Embed
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.entities.Member
import one.devos.yiski.common.utils.EmbedHelpers
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionMessage
import one.devos.yiski1.tables.moderation.InfractionType
import one.devos.yiski1.tables.moderation.InfractionsTable
import one.devos.yiski1.toUUID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.annotations.Name
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.time.Duration
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toKotlinDuration

@SlashCommand(name = "infractions")
class Infractions : Scaffold {
    @SlashSubCommand("Get a specific infraction")
    suspend fun get(ctx: SlashContext, @Name("infraction_id") @Description("ID of the infraction") infractionId: String) {
        val interaction = ctx.interaction.deferReply(true).await()

        val infraction = newSuspendedTransaction { Infraction.findById(infractionId.toUUID()) } ?: return interaction.editOriginal("No infraction found for ID **$infractionId**").queue()

        val user = ctx.jda.retrieveUserById(infraction.userId).await()

        interaction.editOriginalEmbeds(Embed {
            author {
                name = "Infraction #${infraction.id} of @${user.name}"
                iconUrl = ctx.guild?.retrieveMemberById(infraction.userId)?.await()?.effectiveAvatarUrl ?: user.effectiveAvatarUrl
            }
            color = EmbedHelpers.infoColor()

            title = infraction.type.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }

            field("Created at", "<t:${Instant.fromEpochMilliseconds(infraction.createdAt).epochSeconds}:F>")
            field("Moderator", infraction.moderator.let { ctx.guild?.retrieveMemberById(it)?.await()?.user?.asMention ?: "ID: $it" }, true)
            if (infraction.duration > 0) field("Duration", infraction.duration.toDuration(DurationUnit.SECONDS).toDouble(DurationUnit.MINUTES).toString(), true)

            when (infraction.type) {
                InfractionType.NOTE -> {
                    description = infraction.reason
                }

                else -> {
                    field("Reason", infraction.reason)

                    if (infraction.messages.isNotEmpty()) {
                        description = infraction.messages
                            .map { Json.decodeFromString(InfractionMessage.serializer(), it) }
                            .joinToString("\n") { "@${it.authorTag} - ${it.message}" }
                    }
                }
            }
        }).await()
    }

    @SlashSubCommand("List infractions for a user")
    suspend fun list(ctx: SlashContext, @Description("Which user?") member: Member) {
        val interaction = ctx.interaction.deferReply(true).await()

        val infractions = newSuspendedTransaction {
            Infraction
                .find { (InfractionsTable.guildId eq ctx.guild!!.idLong) and (InfractionsTable.userId eq member.user.idLong) }
                .map { infraction ->
                    val moderator = ctx.jda.retrieveUserById(infraction.moderator).complete()
                    "- **${infraction.id}** - **${infraction.type.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }}** - @${moderator.name} - ${infraction.reason.ifEmpty { "No reason provided." }.take(30)}"
                }
        }

        val pages = infractions.chunked(10).map { chunk ->
            Embed {
                author("Infractions for @${member.user.name} in ${ctx.guild!!.name}", null, member.user.effectiveAvatarUrl) {}
                description = if (chunk.isEmpty()) "No infractions found." else {
                    "ID - Type - Moderator - Reason\n"+chunk.joinToString("\n")
                }
                color = EmbedHelpers.infoColor()
            }
        }.toTypedArray()

        interaction.sendPaginator(*pages, expireAfter = Duration.ofMinutes(10).toKotlinDuration()).await()
    }

    @SlashSubCommand("Remove infraction")
    suspend fun remove(ctx: SlashContext, @Name("infraction_id") @Description("The optional entry to remove") infractionId: String) {
        val interaction = ctx.interaction.deferReply(true).await()
        val infraction = newSuspendedTransaction { Infraction.find { (InfractionsTable.guildId eq ctx.guild!!.idLong) and (InfractionsTable.id eq infractionId.toUUID()) }.firstOrNull() }

        if (infraction == null) {
            interaction.editOriginalEmbeds(Embed {
                title = "No infraction could be found for #${infractionId} that belongs to this guild."
                color = EmbedHelpers.failColor()
            })
        } else {
            newSuspendedTransaction {
                infraction.delete()
            }
            interaction.editOriginalEmbeds(Embed {
                title = "Purged infraction #${infraction.id}"
                color = EmbedHelpers.successColor()
            })
        }
    }
}
