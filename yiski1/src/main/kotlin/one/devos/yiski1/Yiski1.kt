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
class Yiski1(
    // Change these to vals if they're needed!
    database: DatabaseManager,
    aviation: Aviation,
    jda: JDA,
    config: Yiski1ConfigData
) : YiskiModuleEntrypoint(
    database,
    aviation,
    jda,
    config
) {

    companion object {
        lateinit var instance: Yiski1
            private set
    }

    init {
        instance = this
    }

//    val db by lazy { Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC") }

    override fun setup() {
        logger.info { "Yiski1 module set up." }
        // TODO!
    }

}
