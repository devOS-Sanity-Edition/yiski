package one.devos.yiski.runner.aviation

import one.devos.yiski.common.YiskiShared
import org.reflections.Reflections
import org.reflections.scanners.MethodParameterNamesScanner
import org.reflections.scanners.Scanners
import org.reflections.util.ConfigurationBuilder
import org.slf4j.LoggerFactory
import xyz.artrinix.aviation.entities.Scaffold
import java.lang.reflect.Modifier

class ModuleScaffoldIndexer(private val packageName: String) {

    private companion object {

        private val logger = LoggerFactory.getLogger(ModuleScaffoldIndexer::class.java)

    }

    private val reflections = Reflections(ConfigurationBuilder.build()
        .forPackage(packageName, YiskiShared.moduleLoader.classLoader)
        .addScanners(MethodParameterNamesScanner())
        .addScanners(Scanners.SubTypes))

    fun getScaffolds(): List<Scaffold> {
        val scaffolds = reflections.getSubTypesOf(Scaffold::class.java)
        logger.debug("Discovered ${scaffolds.size} scaffolds in $packageName")

        return scaffolds
            .filter { !Modifier.isAbstract(it.modifiers) && !it.isInterface && Scaffold::class.java.isAssignableFrom(it) }
            .map { it.getDeclaredConstructor().newInstance() }
    }

}
