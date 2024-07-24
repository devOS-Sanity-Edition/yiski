package one.devos.yiski1.data

import kotlinx.serialization.Serializable
import one.devos.yiski.common.AbstractYiskiConfig

@Serializable
data class Yiski1ConfigData(
    val nothingYet: String
) : AbstractYiskiConfig
