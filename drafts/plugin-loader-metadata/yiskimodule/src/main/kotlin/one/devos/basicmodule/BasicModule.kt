package one.devos.basicmodule

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import one.devos.basicmodule.data.BasicModuleConfigData
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

class BasicModule : YiskiModuleEntrypoint {
    override val moduleName: String = "BasicModule"
    override val moduleDescription: String = ""

    companion object {
        lateinit var instance: BasicModule
            private set
    }

    init {
        instance = this
        logger.info { "BasicModule module loaded." }
    }

    lateinit var config: BasicModuleConfigData

    override fun config() { config = BasicModuleConfig.loadConfig() }

    override fun database(database: DatabaseManager) { }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.basicmodule.commands")
    }

    override fun jda(jda: JDA) { }
}