package one.devos.yiski.module.loader.impl

import io.github.oshai.kotlinlogging.KotlinLogging
import one.devos.yiski.module.loader.api.discovery.ModuleDiscoverer
import one.devos.yiski.module.loader.api.entrypoints.Entrypoint
import one.devos.yiski.module.metadata.ModuleMetadata
import one.devos.yiski.module.metadata.ModuleMetadataReader
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.jar.JarFile

val logger = KotlinLogging.logger {  }

class ModuleLoader {

    private val discoverers = mutableSetOf<ModuleDiscoverer>()
    private val classLoader = ModuleClassLoader(emptyList(), this::class.java.classLoader)

    private val modules = mutableSetOf<ModuleMetadata>()

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

        val output = metadataSources.map(ModuleMetadataReader::read).distinct().toSet()
        this.modules.addAll(output)
        logger.info { "Discovered ${metadataSources.size} modules in ${System.currentTimeMillis() - startTime}ms" }
        return output
    }

    fun getMetadataFor(id: String): ModuleMetadata? {
        return modules.find { mod -> mod.information.id == id }
    }

    fun getModules(): Set<ModuleMetadata> {
        return modules.toSet()
    }

    fun addDiscoverer(discoverer: ModuleDiscoverer) = apply {
        discoverers += discoverer
    }

    /**
     * Loads an entrypoint based off of the class given.
     *
     * This function allows the given class to be a class or an object.
     */
    fun loadEntrypoint(
        clz: Class<*>,
        vararg args: Any
    ): Entrypoint {
        if (clz.kotlin.objectInstance != null || clz.kotlin.isCompanion) {
            throw IllegalArgumentException("Cannot load entrypoint from object or companion object!")
        }

        val constructor = clz.declaredConstructors.first()
        if (constructor.parameterCount != args.size) {
            throw IllegalArgumentException("Invalid number of arguments for entrypoint constructor! (expected ${args.size} - ${args.map { it::class.java.simpleName }.joinToString(", ")})")
        }

        val instance = constructor.newInstance(*args)
        if (instance !is Entrypoint) {
            throw IllegalArgumentException("${clz.name} does not implement Entrypoint interface!")
        }

        return instance
    }

}
