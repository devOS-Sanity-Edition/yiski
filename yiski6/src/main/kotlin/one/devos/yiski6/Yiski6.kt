package one.devos.yiski6

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.AbstractYiskiConfig
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

@YiskiModule
class Yiski6(
    // Change these to vals if they're needed!
    database: DatabaseManager,
    aviation: Aviation,
    jda: JDA
) : YiskiModuleEntrypoint(
    database,
    aviation,
    jda,
    object : AbstractYiskiConfig {}
) {

    companion object {
        lateinit var instance: Yiski6
            private set
    }

    init {
        instance = this
    }

    override fun setup() {
        logger.info { "Yiski6 module loaded." }
    }

}