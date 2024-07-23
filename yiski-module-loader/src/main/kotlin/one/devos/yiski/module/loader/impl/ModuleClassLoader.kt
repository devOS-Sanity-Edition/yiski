package one.devos.yiski.module.loader.impl

import one.devos.yiski.module.loader.exceptions.ModuleLoadingException
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Path

class ModuleClassLoader(
    urls: List<URL>,
    parent: ClassLoader
) : URLClassLoader(urls.toTypedArray(), parent) {

    private val classLoaderExceptions = mutableSetOf<String>()

    init {
        // If any classes should be loaded by the parent class loader, call `addClassLoaderException` with the package or class name
        // f.ex: addClassLoaderException("one.devos.yiski.module.loader.ModuleLoader")
    }

    override fun loadClass(name: String): Class<*> {
        if (classLoaderExceptions.any { name.startsWith(it) }) {
            return parent.loadClass(name)
        }

        return try {
            return super.loadClass(name)
        } catch (e: ClassNotFoundException) {
            throw ModuleLoadingException("Failed to load class $name!", e)
        }
    }

    fun addPath(path: Path) {
        try {
            super.addURL(path.toUri().toURL())
        } catch (e: Exception) {
            throw ModuleLoadingException("Failed to add path to class loader!", e)
        }
    }

    fun addClassLoaderException(className: String) {
        classLoaderExceptions += className
    }

}
