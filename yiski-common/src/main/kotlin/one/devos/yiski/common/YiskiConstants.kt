package one.devos.yiski.common

import net.dv8tion.jda.api.JDAInfo
import one.devos.yiski.common.database.DatabaseManager
import xyz.artrinix.aviation.Aviation
import kotlin.jvm.internal.Intrinsics.Kotlin

object YiskiConstants {
    // I've been told to set this as a getter bc apparently it's good practice. im not gonna question it lmao.
    val version: String get() = YiskiConstants::class.java.`package`.implementationVersion ?: "Development"
    val aviationVersion: String get() = Aviation::class.java.`package`.implementationVersion ?: "Aviation Version isn't available, go ask Storm."
    val jdaVersion: String get() = JDAInfo.VERSION
    val kotlinVersion: String get() = Kotlin::class.java.`package`.implementationVersion ?: "Unknown"
    val config = Config.loadConfig()
    val database = DatabaseManager(config.database)
}
