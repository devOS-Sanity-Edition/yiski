package one.devos.yiski2

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

class Yiski2 : YiskiModuleEntrypoint {
    override val moduleName: String = "Yiski2"
    override val moduleDescription: String = ""

    companion object {
        lateinit var instance: Yiski2
            private set
    }

    init {
        instance = this
        logger.info { "Yiski2 module loaded." }
    }

    override fun config() { }
    override fun database(database: DatabaseManager) { }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski2.commands")
    }

    override fun jda(jda: JDA) { }
}