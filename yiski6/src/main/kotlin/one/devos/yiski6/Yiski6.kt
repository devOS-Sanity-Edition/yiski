package one.devos.yiski6

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.module.loader.api.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

class Yiski6 : YiskiModuleEntrypoint {
    override val moduleName: String = "Yiski6"
    override val moduleDescription: String = ""

    companion object {
        lateinit var instance: Yiski6
            private set
    }

    init {
        instance = this
        logger.info { "Yiski6 module loaded." }
    }

    override fun config() { }
    override fun database(database: DatabaseManager) { }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski6.commands")
    }
    override fun jda(jda: JDA) { }
}