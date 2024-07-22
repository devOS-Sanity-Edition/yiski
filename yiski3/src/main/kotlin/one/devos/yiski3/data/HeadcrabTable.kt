package one.devos.yiski3.data

import kotlinx.serialization.Serializable

@Serializable
data class HeadcrabTable(
    val id: Long,
    val selfattempted: Int,
    val success: Int,
    val fail: Int
)