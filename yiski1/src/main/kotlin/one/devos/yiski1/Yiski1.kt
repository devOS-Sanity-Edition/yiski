package one.devos.yiski1

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.YiskiModuleEntrypoint
import one.devos.yiski1.data.Yiski1ConfigData
import org.jetbrains.exposed.sql.Database
import xyz.artrinix.aviation.Aviation

val yiski1Logger = KotlinLogging.logger { }

class Yiski1 : YiskiModuleEntrypoint {
    override val moduleName: String = "Yiski1"
    override val moduleDescription: String = "Basic commands and moderation [kick/ban/timeout/logging/etc]"

    companion object {
        lateinit var instance: Yiski1
            private set
    }

    init {
        instance = this
        yiski1Logger.info { "Yiski1 module loaded." }
    }

    lateinit var config: Yiski1ConfigData
    val db by lazy { Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC") }

    override fun config() {
        config = Yiski1Config.loadConfig()
    }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski1.commands")
    }

    override fun jda(jda: JDA) {

    }
}