package one.devos.yiski1.commands.moderation

import dev.minn.jda.ktx.coroutines.await
import dev.minn.jda.ktx.messages.Embed
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import one.devos.yiski1.tables.guild.Guild
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionMessage
import one.devos.yiski1.tables.moderation.InfractionType
import one.devos.yiski1.tables.moderation.InfractionsTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import xyz.artrinix.aviation.command.slash.SlashContext
import xyz.artrinix.aviation.command.slash.annotations.Description
import xyz.artrinix.aviation.command.slash.annotations.SlashCommand
import xyz.artrinix.aviation.command.slash.annotations.SlashSubCommand
import java.awt.Color
import java.time.Duration
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@SlashCommand("mod", "Moderation commands", defaultUserPermissions = [Permission.MODERATE_MEMBERS, Permission.BAN_MEMBERS, Permission.KICK_MEMBERS, Permission.MESSAGE_MANAGE])
class Moderation {
    private companion object {
        suspend fun TextChannel._getMessagesAsInfractions(count: Int, authorId: Long, remove: Boolean): Array<String> {
            val messageHistory = this.getHistoryBefore(this.latestMessageIdLong, count).await().retrievedHistory

            if (remove) this.deleteMessages(messageHistory.filter { it.author.idLong == authorId }).await()

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
            }.toTypedArray()
        }
    }

    @SlashSubCommand("List the infractions a user has")
    suspend fun infractions(ctx: SlashContext, @Description("The user to get the infractions of") member: Member, @Description("The optional entry to get more info on") infractionId: String?) {
        val guildConfig = newSuspendedTransaction { Guild.findById(ctx.guild!!.idLong)  } ?: return ctx.sendPrivate("Moderation commands are not enabled in this guild")

        if (infractionId == null) {
            val infractions = newSuspendedTransaction { guildConfig.infractions.filter { it.userId == member.idLong } }

            ctx.sendPrivateEmbed {
                setTitle("Infractions of ${member.user.asTag}")
                setDescription(infractions.sortedBy { it.createdAt }.take(20).joinToString("\n") { "${it.id} - ${it.type.name} : ${it.reason}" })
            }
        } else {
            val infraction = newSuspendedTransaction { guildConfig.infractions.find { it.id.value == UUID.fromString(infractionId) } }

            if (infraction == null) {
                ctx.sendPrivate("No infraction found with id $infractionId")
            } else {
                ctx.sendPrivateEmbed {
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

    @SlashSubCommand("Remove an infraction from a user, removing the associated chat log")
    suspend fun remove_infraction(ctx: SlashContext, @Description("The user to remove the infraction from") member: Member, @Description("The infraction to remove") infractionId: String) {
        val guildConfig = newSuspendedTransaction { Guild.findById(ctx.guild!!.idLong)  } ?: return ctx.sendPrivate("Moderation commands are not enabled in this guild")

        val infraction = newSuspendedTransaction { guildConfig.infractions.find { it.id.value == UUID.fromString(infractionId) } }

        if (infraction == null) {
            ctx.sendPrivate("No infraction found with id $infraction")
        } else {
            newSuspendedTransaction { infraction.delete() }
            ctx.sendPrivate("Removed infraction #$infraction")
        }
    }

    @SlashSubCommand("Warn a user, saving their past 10 messages fom the last 24 hours")
    suspend fun warn(ctx: SlashContext, @Description("The user to warn") member: Member, @Description("The reason for the warning") reason: String, @Description("Amount of messages to keep") message_count: Int? = 30, @Description("Remove the messages") remove: Boolean? = false) {
        val guildConfig = newSuspendedTransaction { Guild.findById(ctx.guild!!.idLong)  } ?: return ctx.sendPrivate("Moderation commands are not enabled in this guild")

        val messages = ctx.textChannel!!._getMessagesAsInfractions(message_count!!, ctx.author.idLong, remove!!)

        val infraction = newSuspendedTransaction { Infraction.new {
            this.guildId = ctx.guild!!.idLong
            this.userId = member.idLong
            this.type = InfractionType.WARN
            this.reason = reason
            this.moderator = ctx.author.idLong
            this.messages = messages.toList()
            this.roles = member.roles.map { it.idLong }.toTypedArray().toList()
            this.createdAt = System.currentTimeMillis()
            this.duration = 0
        } }

        ctx.sendPrivate("Warned ${member.user.asMention} for $reason")

        member.user.openPrivateChannel().await().sendMessageEmbeds(Embed {
            title = "You have been warned in ${ctx.guild!!.name}"
            description = "You have been warned for `$reason`"
            color = Color.RED.rgb
        }).await()
    }

    @SlashSubCommand("Timeout a user, optionally removing their chat log")
    suspend fun timeout(ctx: SlashContext, @Description("The user to timeout") member: Member, @Description("The reason for the timeout") reason: String, @Description("Duration for the timeout") duration: String, @Description("Amount of messages to keep") message_count: Int? = 30, @Description("Remove the messages") remove: Boolean? = false) {
        // TODO check if the user is already timed out before starting another infraction.
        val guildConfig = newSuspendedTransaction { Guild.findById(ctx.guild!!.idLong)  } ?: return ctx.sendPrivate("Moderation commands are not enabled in this guild")

        val messages = ctx.textChannel!!._getMessagesAsInfractions(message_count!!, ctx.author.idLong, remove!!)

        val _duration = Duration.parse(duration)

        member.timeoutFor(_duration).await()
        val endTime = System.currentTimeMillis() + _duration.toMillis()

        newSuspendedTransaction { Infraction.new {
            this.guildId = ctx.guild!!.idLong
            this.userId = member.idLong
            this.type = InfractionType.TIMEOUT
            this.reason = reason
            this.moderator = ctx.author.idLong
            this.messages = messages.toList()
            this.roles = member.roles.map { it.idLong }.toTypedArray().toList()
            this.createdAt = System.currentTimeMillis()
            this.duration = _duration.toSeconds()
            this.endTime = endTime
        } }

        ctx.sendPrivate("Timed out ${member.user.asMention} for $reason")

        member.user.openPrivateChannel().await().sendMessageEmbeds(Embed {
            title = "You have been timed out in ${ctx.guild!!.name}"
            description = "You have been timed out for `$reason`"
            color = Color.RED.rgb
        }).await()
    }

    @SlashSubCommand("Mute a user, saving their past messages and context and optionally removing it.")
    suspend fun mute(ctx: SlashContext, @Description("The user to mute") member: Member, @Description("The reason for the mute") reason: String, @Description("The time to mute the user for") time: String?, @Description("Amount of messages to keep") message_count: Int? = 30, @Description("Remove the messages") remove: Boolean? = false) {
        val guildConfig = newSuspendedTransaction { Guild.findById(ctx.guild!!.idLong)  } ?: return ctx.sendPrivate("Moderation commands are not enabled in this guild")

        val muteRole = guildConfig.muteRole?.let { ctx.guild!!.getRoleById(it) } ?: return ctx.sendPrivate("No mute role set in this guild")

        val existingInfraction = newSuspendedTransaction {
            return@newSuspendedTransaction Infraction
                .find {
                    (InfractionsTable.guildId eq ctx.guild!!.idLong) and ((InfractionsTable.userId eq member.idLong) and (InfractionsTable.type eq InfractionType.MUTE))
                }
                .filterNot { System.currentTimeMillis() > it.endTime }
                .firstOrNull()
        }

        if (existingInfraction != null) {
            return ctx.sendPrivate("${member.asMention} is already muted.")
        }

        val messages = ctx.textChannel!!._getMessagesAsInfractions(message_count!!, ctx.author.idLong, remove!!)

        val duration = time?.let { Duration.parse(it) } ?: Duration.ofSeconds(0)
        val endTime = System.currentTimeMillis() + duration.toMillis()

        newSuspendedTransaction { Infraction.new {
            this.guildId = ctx.guild!!.idLong
            this.userId = member.idLong
            this.type = InfractionType.MUTE
            this.reason = reason
            this.moderator = ctx.author.idLong
            this.messages = messages.toList()
            this.roles = member.roles.map { it.idLong }.toTypedArray().toList()
            this.createdAt = System.currentTimeMillis()
            this.duration = duration.toSeconds()
            this.endTime = endTime
        } }

        ctx.guild!!.modifyMemberRoles(member, listOf(muteRole), member.roles).await()

        member.user.openPrivateChannel().await().sendMessageEmbeds(Embed {
            title = "You have been muted in ${ctx.guild!!.name}"
            description = "You have been muted for `$reason`${if (time != null) ", you will be unmuted in ${duration.toMinutes()}" else ""}"
            color = Color.RED.rgb
        }).await()



        ctx.sendPrivate("Muted ${member.asMention} for $reason")
    }

    @SlashSubCommand("Unmute a user, optionally removing the mute log")
    suspend fun unmute(ctx: SlashContext, @Description("The user to unmute") member: Member, @Description("The reason for the unmute") reason: String?) {
        val guildConfig = newSuspendedTransaction { Guild.findById(ctx.guild!!.idLong)  } ?: return ctx.sendPrivate("Moderation commands are not enabled in this guild")

        val muteRole = guildConfig.muteRole?.let { ctx.guild!!.getRoleById(it) } ?: return ctx.sendPrivate("No mute role set in this guild")

        val existingInfraction = newSuspendedTransaction {
            return@newSuspendedTransaction Infraction
                .find {
                    (InfractionsTable.guildId eq ctx.guild!!.idLong) and ((InfractionsTable.userId eq member.idLong) and (InfractionsTable.type eq InfractionType.MUTE))
                }
                .filterNot { System.currentTimeMillis() > it.endTime }
                .firstOrNull()
        } ?: return ctx.sendPrivate("${member.asMention} is not currently muted.")



    }

    @SlashSubCommand("Kick a user, optionally clearing their chat messages and saving it.")
    suspend fun kick(ctx: SlashContext, @Description("The user to kick") member: Member, @Description("The reason for the kick") reason: String, @Description("Amount of messages to keep") message_count: Int? = 30, @Description("Remove the messages") remove: Boolean? = false) {

    }

    @SlashSubCommand("Ban a user, optionally clearing their chat messages and saving it")
    suspend fun ban(ctx: SlashContext, @Description("The user to ban") member: Member, @Description("The reason for the ban") reason: String, @Description("Amount of messages to keep") message_count: Int? = 30, @Description("Remove the messages") remove: Boolean? = false) {

    }

    @SlashSubCommand("Unban a user, optionally also removing the ban log")
    suspend fun unban(ctx: SlashContext, @Description("The user to unban") member: Member, @Description("The reason for the unban") reason: String?) {

    }
}
