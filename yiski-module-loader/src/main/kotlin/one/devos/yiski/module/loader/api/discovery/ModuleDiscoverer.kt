package one.devos.yiski.module.loader.api.discovery

import java.nio.file.Path

interface ModuleDiscoverer {

    fun discover(): Set<Path>

}
