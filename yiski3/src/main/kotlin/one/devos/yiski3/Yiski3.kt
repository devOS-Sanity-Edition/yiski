package one.devos.yiski3

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski3.data.Yiski3ConfigData
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

class Yiski3 : YiskiModuleEntrypoint {
    override val moduleName: String = "Yiski3"
    override val moduleDescription: String = ""

    companion object {
        lateinit var instance: Yiski3
            private set
    }

    init {
        instance = this
        logger.info { "Yiski3 module loaded." }
    }

    lateinit var config: Yiski3ConfigData

    override fun config() {
        config = Yiski3Config.loadConfig()

    }

    override fun database(database: DatabaseManager) { }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski3.commands")
    }

    override fun jda(jda: JDA) { }
}