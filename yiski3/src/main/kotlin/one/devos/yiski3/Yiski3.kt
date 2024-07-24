package one.devos.yiski3

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski3.data.Yiski3ConfigData
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

@OptIn(YiskiModule::class)
class Yiski3 : YiskiModuleEntrypoint {
    companion object {
        lateinit var instance: Yiski3
            private set
    }

    init {
        instance = this
        config()
        logger.info { "Yiski3 module loaded." }
    }

    lateinit var config: Yiski3ConfigData

    override fun config() {
        config = Yiski3Config.load()
    }

    override fun database(database: DatabaseManager) { }

    override fun aviation(aviation: Aviation) {
    }

    override fun jda(jda: JDA) { }
}