package one.devos.yiski.common.utils

import net.dv8tion.jda.api.JDA
import one.devos.yiski.common.AbstractYiskiConfig
import one.devos.yiski.common.annotations.YiskiModule
import one.devos.yiski.common.database.DatabaseManager
import one.devos.yiski.common.entrypoints.ConfigSetupEntrypoint
import one.devos.yiski.common.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.module.loader.impl.ModuleLoader
import one.devos.yiski.module.metadata.ModuleMetadata
import xyz.artrinix.aviation.Aviation

@OptIn(YiskiModule::class)
fun ModuleLoader.getMainEntrypoint(
    metadata: ModuleMetadata,
    database: DatabaseManager,
    aviation: Aviation,
    jda: JDA,
    config: AbstractYiskiConfig
): YiskiModuleEntrypoint? {
    val name = metadata.runner.mainClass
    if (name.isEmpty()) {
        return null
    }

    val entrypointClass = Class.forName(name)
    val entrypoint = loadEntrypoint(entrypointClass, database, aviation, jda, config)
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
