package one.devos.yiski.common.entrypoints

import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.AbstractYiskiConfig
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.module.loader.api.entrypoints.Entrypoint
import xyz.artrinix.aviation.Aviation

@YiskiModule
abstract class YiskiModuleEntrypoint(
    database: DatabaseManager,
    aviation: Aviation,
    jda: JDA,
    config: AbstractYiskiConfig?
) : Entrypoint {

    abstract fun setup()

}
