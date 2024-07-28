package one.devos.yiski3.tables

import one.devos.yiski.common.annotations.DatabaseEntry
import one.devos.yiski3.data.HeadcrabSuccess
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

@DatabaseEntry
object HeadcrabTable : UUIDTable("headcrabs") {
    val discordId = long("discordId")
    val incident = long("incident")
    val selfAttempted = bool("selfAttempted")
    val successType = enumeration("successType", HeadcrabSuccess::class)
    val reason = text("reason")
}

class Headcrab(uuid: EntityID<UUID>) : UUIDEntity(uuid) {
    companion object : UUIDEntityClass<Headcrab>(HeadcrabTable)

    var discordId by HeadcrabTable.discordId
    var incident by HeadcrabTable.incident
    var selfAttempted by HeadcrabTable.selfAttempted
    var successType by HeadcrabTable.successType
    var reason by HeadcrabTable.reason
}