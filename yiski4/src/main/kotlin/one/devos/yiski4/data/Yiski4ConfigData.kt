package one.devos.yiski4.data

import kotlinx.serialization.Serializable
import one.devos.yiski.common.AbstractYiskiConfig

@Serializable
data class Yiski4ConfigData(
    val agent: Agent
) : AbstractYiskiConfig {

    @Serializable
    data class Agent(
        val ip: List<String>,
        val isRPi: Boolean,
        val username: String,
        val password: String
    )
}
