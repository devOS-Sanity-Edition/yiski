package one.devos.yiski5

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.YiskiModuleEntrypoint
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

class Yiski5 : YiskiModuleEntrypoint {
    override val moduleName: String = "Yiski5"
    override val moduleDescription: String = ""

    companion object {
        lateinit var instance: Yiski5
            private set
    }

    init {
        instance = this
        logger.info { "Yiski5 module loaded." }
    }

    override fun config() {

    }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski5.commands")
    }

    override fun jda(jda: JDA) { }
}