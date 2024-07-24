package one.devos.yiski.common.utils

import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.entrypoints.ConfigSetupEntrypoint
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.module.loader.impl.ModuleLoader
import one.devos.yiski.module.metadata.ModuleMetadata

@OptIn(YiskiModule::class)
fun ModuleLoader.getMainEntrypoint(metadata: ModuleMetadata): YiskiModuleEntrypoint? {
    val name = metadata.runner.mainClass
    if (name.isEmpty()) {
        return null
    }

    val entrypointClass = Class.forName(name)
    val entrypoint = loadEntrypoint(entrypointClass)
    if (entrypoint !is YiskiModuleEntrypoint) {
        throw IllegalArgumentException("$name does not implement YiskiModuleEntrypoint interface!")
    }

    return entrypoint
}

@OptIn(YiskiModule::class)
fun ModuleLoader.getConfigSetupEntrypoint(metadata: ModuleMetadata): ConfigSetupEntrypoint? {
    val name = metadata.module.configClass
    if (name.isEmpty()) {
        return null
    }

    val entrypointClass = Class.forName(name)
    val entrypoint = loadEntrypoint(entrypointClass)
    if (entrypoint !is ConfigSetupEntrypoint) {
        throw IllegalArgumentException("$name does not implement ConfigSetupEntrypoint interface!")
    }

    return entrypoint
}
