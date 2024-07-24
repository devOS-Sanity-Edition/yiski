package one.devos.yiski1

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski1.data.Yiski1ConfigData
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

@OptIn(YiskiModule::class)
class Yiski1 : YiskiModuleEntrypoint {
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
        config = config
    }

    override fun database(database: DatabaseManager) {
    }

    override fun aviation(aviation: Aviation) {

    }

    override fun jda(jda: JDA) {

    }
}