package one.devos.yiski.runner.data

import kotlinx.serialization.Serializable
import one.devos.yiski.common.YiskiConstants

@Serializable
data class VersionsResponse(
    val yiski: String = YiskiConstants.version,
    val aviation: String = YiskiConstants.aviationVersion,
    val jda: String = YiskiConstants.jdaVersion,
    val kotlin: String = YiskiConstants.kotlinVersion,
)
