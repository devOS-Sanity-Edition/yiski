package one.devos.yiski.common

import one.devos.yiski.module.loader.impl.ModuleLoader

object YiskiShared {

    lateinit var moduleLoader: ModuleLoader
        private set

    fun initializeModuleLoader(loader: ModuleLoader) {
        if (::moduleLoader.isInitialized) {
            throw IllegalStateException("Module loader has already been initialized!")
        }

        this.moduleLoader = loader
    }

}
