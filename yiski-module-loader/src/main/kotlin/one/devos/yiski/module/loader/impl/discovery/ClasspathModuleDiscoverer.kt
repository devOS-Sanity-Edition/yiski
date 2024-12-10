package one.devos.yiski.module.loader.impl.discovery

import one.devos.yiski.module.loader.api.discovery.ModuleDiscoverer
import one.devos.yiski.module.loader.impl.ModuleLoader
import one.devos.yiski.module.loader.impl.logger
import one.devos.yiski.module.metadata.ModuleMetadata
import java.net.URI
import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

class ClasspathModuleDiscoverer : ModuleDiscoverer {

    override fun discover(): Set<Path> {
        val result = mutableSetOf<Path>()
        val fileEnumeration = ModuleLoader::class.java.classLoader.getResources(ModuleMetadata.FILE_NAME)

        while (fileEnumeration.hasMoreElements()) {
            val url = fileEnumeration.nextElement()
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
            return Paths.get(URI(url.protocol, url.host, url.port.toString(), urlPath.substring(0, urlPath.length - path.length)))

        throw IllegalStateException("Failed to fetch code source for file \"$path\" inside \"$url\"!")
    }

}
