package one.devos.yiski.tables

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.intParam

object Headcrabs : LongIdTable("headcrabs") {
    val discordId = long("discordId")
    val selfattempted = integer("selfattempted")
    val success = long("success")
    val fail = long("fail")
}

//class Headcrab(id: EntityID<Long>) : Long {
//}