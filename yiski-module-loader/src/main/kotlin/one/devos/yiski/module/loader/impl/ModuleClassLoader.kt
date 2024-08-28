package one.devos.yiski.module.loader.impl

import one.devos.yiski.module.loader.exceptions.ModuleLoadingException
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class ModuleClassLoader(parent: ClassLoader) : URLClassLoader(getDefaultClasspath(), parent) {

    private companion object {

        private fun getDefaultClasspath(): Array<URL> {
            val result = mutableListOf<URL>()
            for (entry in System.getProperty("java.class.path").split(File.pathSeparator)) {
                if (entry == "*" || entry.endsWith(File.pathSeparator + "*")) {
                    continue
                }

                val path = Paths.get(entry)
                if (!Files.exists(path)) {
                    continue
                }

                result.add(path.toUri().toURL())
            }

            return result.toTypedArray()
        }

    }

    private val loaderExceptions = mutableSetOf<String>()

    init {
        loaderExceptions.add("java.")
        loaderExceptions.add("javax.")
        loaderExceptions.add("kotlin.")
        loaderExceptions.add("kotlinx.")
        loaderExceptions.add("sun.")
        loaderExceptions.add("com.sun.")
        loaderExceptions.add("jdk.")
        loaderExceptions.add("org.w3c.")

        loaderExceptions.add("one.devos.yiski.module.")
        loaderExceptions.add("one.devos.yiski.common.")

        loaderExceptions.add("net.dv8tion.jda.")
        loaderExceptions.add("org.jetbrains.exposed.")
        loaderExceptions.add("xyz.artrinix.aviation.")
    }

    override fun loadClass(name: String): Class<*> {
        if (loaderExceptions.any { exception ->
                name.startsWith(exception)
            }) {
            return parent.loadClass(name)
        }

        val loadedClass = findLoadedClass(name)
        if (loadedClass != null) {
            return loadedClass
        }

        return try {
            synchronized(getClassLoadingLock(name)) {
                try {
                    super.findClass(name)
                } catch (e: ClassNotFoundException) {
                    parent.loadClass(name)
                }
            }
        } catch (_: SecurityException) {
            parent.loadClass(name)
        } catch (_: ClassNotFoundException) {
            parent.loadClass(name)
        } catch (e: Exception) {
            throw ModuleLoadingException("Failed to load class $name!", e)
        }
    }

    override fun loadClass(name: String, resolve: Boolean): Class<*> {
        val result = loadClass(name)
        if (resolve) {
            resolveClass(result)
        }

        return result
    }

    fun addPath(path: Path) {
        try {
            super.addURL(path.toUri().toURL())
        } catch (e: Exception) {
            throw ModuleLoadingException("Failed to add path to class loader!", e)
        }
    }

}
