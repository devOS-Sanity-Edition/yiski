package one.devos.yiski4

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.AbstractYiskiConfig
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski4.data.Yiski4ConfigData
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

@YiskiModule
class Yiski4(
    // Change these to vals if they're needed!
    database: DatabaseManager,
    aviation: Aviation,
    jda: JDA,
    private val config: Yiski4ConfigData
) : YiskiModuleEntrypoint(
    database,
    aviation,
    jda,
    config
) {
    companion object {
        lateinit var instance: Yiski4
            private set

        val config: Yiski4ConfigData
            get() = instance.config
    }

    init {
        instance = this
    }

    override fun setup() {
        logger.info { "Yiski4 module loaded." }
    }

}