package one.devos.yiski1.data

import kotlinx.serialization.Serializable

@Serializable
data class HeadcrabTable(
    val id: Long,
    val selfattempted: Int,
    val success: Int,
    val fail: Int
)