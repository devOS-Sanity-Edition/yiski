package one.devos.yiski.module.loader.impl.discovery

import one.devos.yiski.module.loader.api.discovery.ModuleDiscoverer
import one.devos.yiski.module.loader.impl.ModuleLoader
import one.devos.yiski.module.loader.impl.logger
import one.devos.yiski.module.metadata.ModuleMetadata
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

class ClasspathModuleDiscoverer : ModuleDiscoverer {

    override fun discover(): Set<Path> {
        val result = mutableSetOf<Path>()
        val modEnumeration = ModuleLoader::class.java.classLoader.getResources(ModuleMetadata.FILE_NAME)

        while (modEnumeration.hasMoreElements()) {
            val url = modEnumeration.nextElement()
            try {
                val path = fetchCodeSource(url, ModuleMetadata.FILE_NAME)
                result.add(path)
            } catch (e: Exception) {
                logger.error(e) { "Failed to find location of ${ModuleMetadata.FILE_NAME} file from $url" }
            }
        }

        return result
    }

    private fun fetchCodeSource(url: URL, path: String): Path {
        val urlPath = url.path
        if (urlPath.endsWith(path))
            return Paths.get(URL(url.protocol, url.host, url.port, urlPath.substring(0, urlPath.length - path.length)).toURI())

        throw IllegalStateException("Failed to fetch code source for file \"$path\" inside \"$url\"!")
    }

}
