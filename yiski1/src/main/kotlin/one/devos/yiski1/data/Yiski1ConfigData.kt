package one.devos.yiski1.data

import kotlinx.serialization.Serializable
import one.devos.yiski.common.AbstractYiskiConfig

@Serializable
data class Yiski1ConfigData(
    val moderation: Moderation
) : AbstractYiskiConfig {
    @Serializable
    data class Moderation(
        val muteRoleID: Long
    )
}
