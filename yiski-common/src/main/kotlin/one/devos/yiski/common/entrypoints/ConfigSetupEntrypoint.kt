package one.devos.yiski.common.entrypoints

import one.devos.yiski.module.loader.api.entrypoints.Entrypoint

interface ConfigSetupEntrypoint : Entrypoint {

    fun load()

}
