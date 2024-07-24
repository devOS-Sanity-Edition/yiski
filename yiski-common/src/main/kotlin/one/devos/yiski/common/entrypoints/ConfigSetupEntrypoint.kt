package one.devos.yiski.common.entrypoints

import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.module.loader.api.entrypoints.Entrypoint

@YiskiModule
interface ConfigSetupEntrypoint : Entrypoint {

    fun load()

}
