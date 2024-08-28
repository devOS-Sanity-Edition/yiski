package one.devos.yiski.runner

import one.devos.yiski.common.YiskiShared
import one.devos.yiski.module.loader.impl.ModuleClassLoader
import one.devos.yiski.module.loader.impl.ModuleLoader

object YiskiRunner {

    @JvmStatic
    fun main(args: Array<String>) {
        val baseClassLoader = this::class.java.classLoader
        val classLoader = ModuleClassLoader(baseClassLoader)
        Thread.currentThread().contextClassLoader = classLoader
        YiskiShared.initializeModuleLoader(ModuleLoader(classLoader))

        bootstrap(classLoader)
    }

    private fun bootstrap(classLoader: ClassLoader) {
        val clz = classLoader.loadClass("one.devos.yiski.runner.Bootstrapper")
        val instance = clz.getDeclaredField("INSTANCE").get(null)
        clz.getDeclaredMethod("start").invoke(instance)
    }

}
