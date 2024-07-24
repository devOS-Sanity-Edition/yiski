package one.devos.yiski4

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

@OptIn(YiskiModule::class)
class Yiski4 : YiskiModuleEntrypoint {
    companion object {
        lateinit var instance: Yiski4
            private set
    }

    init {
        instance = this
        logger.info { "Yiski4 module loaded." }
    }

    override fun config() { }

    override fun database(database: DatabaseManager) { }

    override fun aviation(aviation: Aviation) {
    }

    override fun jda(jda: JDA) { }
}