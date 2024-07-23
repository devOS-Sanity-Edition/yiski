package one.devos.yiski.common

import net.dv8tion.jda.api.JDAInfo
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.module.loader.impl.ModuleLoader
import xyz.artrinix.aviation.Aviation

object YiskiConstants {
    // I've been told to set this as a getter bc apparently it's good practice. im not gonna question it lmao.
    val version: String get() = YiskiConstants::class.java.`package`.implementationVersion ?: "DEV"
    val aviationVersion: String get() = Aviation::class.java.`package`.implementationVersion ?: "CANNOT GET AVIATION VERSION. IF YOU'RE SEEING THIS... *WELL FUCK*!"
    val jdaVersion: String get() = JDAInfo.VERSION
    val config = Config.loadConfig()
    val moduleLoader = ModuleLoader()
    val database = DatabaseManager(config.database)
}
