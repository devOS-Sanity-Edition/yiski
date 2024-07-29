package one.devos.yiski1.tables.guild

import one.devos.yiski.common.annotations.DatabaseEntry
import one.devos.yiski1.tables.moderation.Infraction
import one.devos.yiski1.tables.moderation.InfractionsTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table

@DatabaseEntry
object GuildToInfractionTable : Table("guild_to_infraction") {
    val guild = reference("guild_id", GuildTable)
    val infraction = reference("infraction_id", InfractionsTable)
    override val primaryKey = PrimaryKey(guild, infraction, name = "PK_GuildToInfraction_swf_act")
}

@DatabaseEntry
object GuildTable : LongIdTable("guild") {
//    val restoreRolesOnRejoin = bool("restore_roles_on_rejoin").default(false)
    val muteRole = long("mute_role").nullable()
}

//class GuildToInfraction(id: EntityID<Long>) : LongEntity(id) { // this isnt correct- shit.
//    companion object : LongEntityClass<GuildToInfraction>(GuildToInfractionTable)
//
//    var infraction by GuildToInfractionTable.infraction
//}

class Guild(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Guild>(GuildTable)

//    var restoreRolesOnRejoin by GuildTable.restoreRolesOnRejoin
    var muteRole by GuildTable.muteRole
    var infractions by Infraction via GuildToInfractionTable
}

