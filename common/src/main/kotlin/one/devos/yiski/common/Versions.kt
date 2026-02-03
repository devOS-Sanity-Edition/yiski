package one.devos.yiski.common

import java.util.Properties

object Versions {
    val yiskiProperties: Properties by lazy {
        val stream = Versions::class.java.getResourceAsStream("/yiski.properties") ?: throw NullPointerException("Yiski properties not found")
        val props = Properties()
        props.load(stream)
        props
    }

    val KORD_VERSION: String by lazy {
        yiskiProperties.getProperty("versions.kord")?: "Unknown"
    }

    val MOEKA_VERSION: String by lazy {
        yiskiProperties.getProperty("versions.moeka") ?: "Unknown"
    }

    val YISKI_VERSION: String by lazy {
        yiskiProperties.getProperty("versions.yiski") ?: "Unknown"
    }
}