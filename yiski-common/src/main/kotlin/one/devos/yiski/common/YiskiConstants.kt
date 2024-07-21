package one.devos.yiski.common

import net.dv8tion.jda.api.JDAInfo
import xyz.artrinix.aviation.Aviation

object YiskiConstants {
    // I've been told to set this as a getter bc apparently it's good practice. im not gonna question it lmao.
    val version: String get() = YiskiConstants::class.java.`package`.implementationVersion ?: "DEV"
    val aviationVersion: String get() = Aviation::class.java.`package`.implementationVersion
    val jdaVersion: String get() = JDAInfo.VERSION
    val config = Config.loadConfig()
}