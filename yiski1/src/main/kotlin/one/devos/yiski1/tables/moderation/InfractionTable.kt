package one.devos.yiski1.tables.moderation

import one.devos.yiski.common.annotations.DatabaseEntry
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.LongColumnType
import org.jetbrains.exposed.sql.TextColumnType
import java.util.*

@DatabaseEntry
object InfractionsTable : UUIDTable("infractions") {
    val guildId = long("guild_id")
    val userId = long("user_id")
    val type = enumeration("type", InfractionType::class)
    val reason = text("reason")
    val moderator = long("moderator_id")
    val messages = array("messages", TextColumnType()).default(listOf())
    val roles = array("roles", LongColumnType()).default(listOf())
    val createdAt = long("created_at")
    val duration = long("duration")
    val endTime = long("end_time")
}

class Infraction(uuid: EntityID<UUID>) : UUIDEntity(uuid) {
    companion object : UUIDEntityClass<Infraction>(InfractionsTable)

    var guildId by InfractionsTable.guildId
    var userId by InfractionsTable.userId
    var type by InfractionsTable.type
    var reason by InfractionsTable.reason
    var moderator by InfractionsTable.moderator
    var messages by InfractionsTable.messages
    var roles by InfractionsTable.roles
    var createdAt by InfractionsTable.createdAt
    var duration by InfractionsTable.duration
    var endTime by InfractionsTable.endTime
}
