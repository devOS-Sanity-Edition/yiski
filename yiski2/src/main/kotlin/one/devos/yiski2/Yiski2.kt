package one.devos.yiski2

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.AbstractYiskiConfig
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.common.database.DatabaseManager
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

@OptIn(YiskiModule::class)
class Yiski2(
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
        lateinit var instance: Yiski2
            private set
    }

    init {
        instance = this
    }

    override fun setup() {
        logger.info { "Yiski2 module loaded." }
    }

}
