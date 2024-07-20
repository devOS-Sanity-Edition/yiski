package one.devos.yiski.common

import net.dv8tion.jda.api.JDA
import xyz.artrinix.aviation.Aviation

interface YiskiModuleEntrypoint {
    val moduleName: String
    val moduleDescription: String
    fun config()
    fun aviation(aviation: Aviation)
    fun jda(jda: JDA)
}