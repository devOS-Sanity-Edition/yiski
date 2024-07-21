package one.devos.yiski1.tables

import org.jetbrains.exposed.dao.id.LongIdTable

object Headcrabs : LongIdTable("headcrabs") {
    val discordId = long("discordId")
    val selfattempted = integer("selfattempted")
    val success = long("success")
    val fail = long("fail")
}

//class Headcrab(id: EntityID<Long>) : Long {
//}