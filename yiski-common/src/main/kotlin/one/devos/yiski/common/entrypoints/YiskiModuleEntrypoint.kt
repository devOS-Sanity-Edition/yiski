package one.devos.yiski.common.entrypoints

import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.module.loader.api.entrypoints.Entrypoint
import xyz.artrinix.aviation.Aviation

interface YiskiModuleEntrypoint : Entrypoint {
    fun config()
    fun database(database: DatabaseManager)
    fun aviation(aviation: Aviation)
    fun jda(jda: JDA)
}