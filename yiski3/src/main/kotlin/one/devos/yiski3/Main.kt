package one.devos.yiski3

import io.github.oshai.kotlinlogging.KotlinLogging
import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.YiskiModuleEntrypoint
import org.jetbrains.exposed.sql.Database
import xyz.artrinix.aviation.Aviation

val yiski3Logger = KotlinLogging.logger { }


class Yiski3 : YiskiModuleEntrypoint {
    override val moduleName: String = "Yiski3"
    override val moduleDescription: String = "Fun and quirky commands"

    companion object {
        lateinit var instance: Yiski3
            private set
    }

    init {
        instance = this
        yiski3Logger.info { "Yiski3 module loaded." }
    }

//    lateinit var config: Yiski3ConfigData
    val db by lazy { Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC") }

    override fun config() {
//        config = Yiski3Config.loadConfig()
    }

    override fun aviation(aviation: Aviation) {
        aviation.slashCommands.register("one.devos.yiski3.commands")
    }

    override fun jda(jda: JDA) {

    }
}