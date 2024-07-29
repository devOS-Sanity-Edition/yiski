package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import one.devos.yiski1.tables.guild.Guild
import one.devos.yiski1.tables.moderation.InfractionMessage
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import xyz.artrinix.aviation.entities.Scaffold
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@SlashCommand(name = "infractions")
class Infractions : Scaffold {
    private companion object {
        suspend fun TextChannel._getMessagesAsInfractions(count: Int, authorId: Long): List<String> {
            val messageHistory = this.getHistoryBefore(this.latestMessageIdLong, count).await().retrievedHistory.filter { it.author.idLong == authorId }

            return messageHistory.map {
                Json.encodeToString(
                    InfractionMessage.serializer(),
                    InfractionMessage(
                        it.author.idLong,
                        it.author.asTag,
                        it.idLong,
                        it.contentRaw,
                        it.timeCreated.toEpochSecond()
                    )
                )
            }
        }
    }

    @SlashSubCommand("List infractions for a user")
    suspend fun list(ctx: SlashContext, @Description("Which user?") member: Member, @Description("The optional entry to get more info on") infractionid: String?) {
        val guildConfig = newSuspendedTransaction { Guild.findById(ctx.guild!!.idLong)  } ?: return ctx.sendPrivate("Moderation commands are not enabled in this guild")
        if (infractionid == null) {
            val infractions = newSuspendedTransaction { guildConfig.infractions.filter { it.userId == member.idLong } }

            ctx.sendEmbed {
                setTitle("Infractions of ${member.user.asTag}")
                setDescription(infractions.sortedBy { it.createdAt }.take(20).joinToString("\n") { "${it.id} - ${it.type.name} : ${it.reason}" })
            }
        } else {
            val infraction = newSuspendedTransaction { guildConfig.infractions.find { it.id.value == UUID.fromString(infractionid) } }

            if (infraction == null) {
                ctx.send("No infraction found with id $infractionid")
            } else {
                ctx.sendEmbed {
                    setAuthor("Infraction #${infraction.id} of ${member.user.asTag} - ${infraction.type.name.lowercase().replaceFirstChar { it.titlecase(Locale.getDefault()) }}", null, member.avatarUrl)
                    setTitle(infraction.reason)
                    if (infraction.messages.isNotEmpty()) {
                        setDescription(infraction.messages.map { Json.decodeFromString(InfractionMessage.serializer(), it) }.joinToString("\n") { "${it.authorTag} - ${it.message}" })
                    }
                    addField("Created at", Instant.fromEpochMilliseconds(infraction.createdAt).toLocalDateTime(TimeZone.UTC).toString(), true)
                    addField("Moderator", infraction.moderator.let { ctx.guild!!.getMemberById(it)?.asMention ?: it.toString() }, true)
                    addField("Duration", infraction.duration.toDuration(DurationUnit.SECONDS).toDouble(DurationUnit.MINUTES).toString(), true)
                }
            }
        }
    }

    @SlashSubCommand("Remove infraction")
    suspend fun remove(ctx: SlashContext, @Description("Which user?") member: Member, @Description("The optional entry to remove") infractionid: String?) {
        val guildConfig = newSuspendedTransaction { Guild.findById(ctx.guild!!.idLong)  } ?: return ctx.sendPrivate("Moderation commands are not enabled in this guild")

        val infraction = newSuspendedTransaction { guildConfig.infractions.find { it.id.value == UUID.fromString(infractionid) } }

        if (infraction == null) {
            ctx.sendPrivate("No infraction found with id $infraction")
        } else {
            newSuspendedTransaction { infraction.delete() }
            ctx.sendPrivate("Removed infraction #$infraction")
        }

    }
}
