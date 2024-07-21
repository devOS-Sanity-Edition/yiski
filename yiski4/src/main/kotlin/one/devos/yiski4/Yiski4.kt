package one.devos.yiski4

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.YiskiModuleEntrypoint
import xyz.artrinix.aviation.Aviation

val logger = KotlinLogging.logger { }

class Yiski4 : YiskiModuleEntrypoint {
    override val moduleName: String = "Yiski4"
    override val moduleDescription: String = ""

    companion object {
        lateinit var instance: Yiski4
            private set
    }

    init {
        instance = this
        logger.info { "Yiski4 module loaded." }
    }

    override fun config() { }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski4.commands")
    }

    override fun jda(jda: JDA) { }
}