package one.devos.yiski.runner.aviation

import one.devos.yiski.common.YiskiShared
import org.reflections.Reflections
import org.reflections.scanners.MethodParameterNamesScanner
import org.reflections.scanners.Scanners
import org.reflections.util.ConfigurationBuilder
import xyz.artrinix.aviation.entities.Scaffold
import java.lang.reflect.Modifier

internal object ModuleScaffoldIndexer {

    private val reflections = Reflections(ConfigurationBuilder.build()
        .addClassLoaders(YiskiShared.moduleLoader.classLoader)
        .addScanners(MethodParameterNamesScanner(), Scanners.SubTypes)
    )

    fun getScaffolds(): List<Scaffold> {
        val scaffolds = reflections.getSubTypesOf(Scaffold::class.java)

        return scaffolds
            .filter { !Modifier.isAbstract(it.modifiers) && !it.isInterface && Scaffold::class.java.isAssignableFrom(it) }
            .map { it.getDeclaredConstructor().newInstance() }
    }

}
