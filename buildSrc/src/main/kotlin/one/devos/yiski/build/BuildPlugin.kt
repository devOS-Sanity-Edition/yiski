package one.devos.yiski.build

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.bundling.Jar

class BuildPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val shade = project.configurations.create("shade")

        val fatJar = project.tasks.register("fatJar", ShadowJar::class.java) {
            group = "devOS"
            description = "Creates a fat JAR with all dependencies"

            configurations.add(shade)
            manifest.inheritFrom(project.tasks.named("jar", Jar::class.java).get().manifest)
            from(project.extensions.getByType(SourceSetContainer::class.java).named("main").get().output)
        }

        project.artifacts.add("shade", fatJar)

        if (
            project.plugins.hasPlugin("java") ||
            project.plugins.hasPlugin("java-library") ||
            project.plugins.hasPlugin("org.jetbrains.kotlin.jvm")
        ) {
            project.tasks.named("assemble").get().dependsOn(fatJar)
        }
    }

}
