package one.devos.yiski.module.loader.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import one.devos.yiski.module.loader.api.discovery.ModuleDiscoverer
import one.devos.yiski.module.loader.api.entrypoints.ConfigSetupEntrypoint
import one.devos.yiski.module.loader.api.entrypoints.Entrypoint
import one.devos.yiski.module.loader.api.entrypoints.YiskiModuleEntrypoint
import one.devos.yiski.module.metadata.ModuleMetadata
import one.devos.yiski.module.metadata.ModuleMetadataReader
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.jar.JarFile

val logger = KotlinLogging.logger {  }

class ModuleLoader {

    private val discoverers = mutableSetOf<ModuleDiscoverer>()
    private val classLoader = ModuleClassLoader(emptyList(), this::class.java.classLoader)

    fun discover(): Set<ModuleMetadata> {
        val startTime = System.currentTimeMillis()
        logger.info { "Discovering modules..." }

        val modulePaths = discoverers.map(ModuleDiscoverer::discover).flatten().distinct()
        val metadataSources = mutableSetOf<String>()
        for (path in modulePaths) {
            val file = path.toFile()
            if (file.isFile) {
                val jar = JarFile(file)
                val entry = jar.getJarEntry(ModuleMetadata.FILE_NAME)
                if (entry == null) {
                    logger.warn { "Module metadata not found in $path" }
                    continue
                }

                val metadata = jar.getInputStream(entry)
                val outputStream = ByteArrayOutputStream()
                metadata.copyTo(outputStream)
                metadataSources.add(String(outputStream.toByteArray(), StandardCharsets.UTF_8))

                classLoader.addPath(file.toPath())
            } else if (file.isDirectory) {
                metadataSources.add(file.resolve(ModuleMetadata.FILE_NAME).readText())
            } else {
                logger.warn { "Invalid module path: $path" }
            }
        }

        logger.info { "Discovered ${metadataSources.size} modules in ${System.currentTimeMillis() - startTime}ms" }
        return metadataSources.map(ModuleMetadataReader::read).distinct().toSet()
    }

    fun getMainEntrypoint(metadata: ModuleMetadata): YiskiModuleEntrypoint {
        val entrypointClass = Class.forName(metadata.runner.mainClass)
        val entrypoint = loadEntrypoint(entrypointClass)
        if (entrypoint !is YiskiModuleEntrypoint) {
            throw IllegalArgumentException("${metadata.runner.mainClass} does not implement YiskiModuleEntrypoint interface!")
        }

        return entrypoint
    }

    fun getConfigSetupEntrypoint(metadata: ModuleMetadata): ConfigSetupEntrypoint {
        val entrypointClass = Class.forName(metadata.module.configClass)
        val entrypoint = loadEntrypoint(entrypointClass)
        if (entrypoint !is ConfigSetupEntrypoint) {
            throw IllegalArgumentException("${metadata.module.configClass} does not implement ConfigSetupEntrypoint interface!")
        }

        return entrypoint
    }

    fun addDiscoverer(discoverer: ModuleDiscoverer) = apply {
        discoverers += discoverer
    }

    /**
     * Loads an entrypoint based off of the class given.
     *
     * This function allows the given class to be a class or an object.
     */
    private fun loadEntrypoint(clz: Class<*>): Entrypoint {
        val instance = (clz.kotlin.objectInstance ?: clz.declaredConstructors.first().newInstance())
        if (instance !is Entrypoint) {
            throw IllegalArgumentException("${clz.name} does not implement Entrypoint interface!")
        }

        return instance
    }

}
