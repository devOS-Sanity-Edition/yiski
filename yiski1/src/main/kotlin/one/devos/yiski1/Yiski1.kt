package one.devos.yiski1

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.YiskiModuleEntrypoint
import one.devos.yiski.common.data.YiskiBotConfig
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski1.data.Yiski1ConfigData
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

class Yiski1 : YiskiModuleEntrypoint {
    override val moduleName: String = "Yiski1"
    override val moduleDescription: String = "Basic commands and moderation [kick/ban/timeout/logging/etc]"

    companion object {
        lateinit var instance: Yiski1
            private set
    }

    init {
        instance = this
        logger.info { "Yiski1 module loaded." }
    }

//    val db by lazy { Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC") }
    lateinit var config: Yiski1ConfigData

    override fun config() {
        config = Yiski1Config.loadConfig()
    }

    override fun database(database: DatabaseManager) {
        database.registerTables("one.devos.yiski1.tables")
    }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski1.commands")
    }

    override fun jda(jda: JDA) {

    }
}