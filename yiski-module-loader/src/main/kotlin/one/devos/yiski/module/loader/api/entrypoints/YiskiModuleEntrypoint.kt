package one.devos.yiski.module.loader.api.entrypoints

import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.database.DatabaseManager
import xyz.artrinix.aviation.Aviation

interface YiskiModuleEntrypoint : Entrypoint {
    val moduleName: String
    val moduleDescription: String
    fun config()
    fun database(database: DatabaseManager)
    fun aviation(aviation: Aviation)
    fun jda(jda: JDA)
}