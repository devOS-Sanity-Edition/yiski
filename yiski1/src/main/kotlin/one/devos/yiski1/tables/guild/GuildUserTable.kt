package one.devos.yiski1.tables.guild

import one.devos.yiski.common.annotations.DatabaseEntry
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

@DatabaseEntry
object GuildUserTable : UUIDTable("guild_user") {
    val guild = long("guild_id")
    val user = long("user_id")
}

class GuildUser(uuid: EntityID<UUID>) : UUIDEntity(uuid) {
    companion object : UUIDEntityClass<GuildUser>(GuildUserTable)

    var guild by GuildUserTable.guild
    var user by GuildUserTable.user
}
