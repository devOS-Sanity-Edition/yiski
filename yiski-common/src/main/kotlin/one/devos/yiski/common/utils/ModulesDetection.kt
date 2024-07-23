package one.devos.yiski.common.utils

import one.devos.yiski.module.loader.api.entrypoints.YiskiModuleEntrypoint
import java.util.*

object ModulesDetection {
    fun detectModules(): Set<YiskiModuleEntrypoint> {
        val result = mutableSetOf<YiskiModuleEntrypoint>()
        val serviceLoader = ServiceLoader.load(YiskiModuleEntrypoint::class.java)
        val iterator = serviceLoader.iterator()
        while (iterator.hasNext()) {
            val module = iterator.next()
            result += module
        }

        return result
    }
}