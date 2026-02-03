import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.shadow)
    alias(libs.plugins.versioning.git)
}

subprojects {
    apply(plugin = rootProject.libs.plugins.kotlin.jvm.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.serialization.get().pluginId)
    apply(plugin = rootProject.libs.plugins.kotlin.kapt.get().pluginId)
    apply(plugin = rootProject.libs.plugins.shadow.get().pluginId)
    apply(plugin = rootProject.libs.plugins.versioning.git.get().pluginId)

    group = "one.devos"
    version = "0.0.0-SNAPSHOT"

    dependencies {
        if (project.name != "common") {
            api(project(":common"))
        }
    }

    kotlin {
        jvmToolchain(25)
    }

    gitVersioning.apply {
        refs {
            branch(".+") {
                version = "\${ref}-\${commit.short}\${dirty.snapshot}"
            }
            tag("v(?<version>.*)") {
                version = "\${ref.version}"
            }
        }

        rev {
            version = "\${commit}"
        }
    }

    tasks {
        jar {
            manifest {
                attributes(
                    mapOf(
                        "Implementation-Title" to project.name,
                        "Implementation-Version" to project.version,
                        "Implementation-Vendor" to project.group,
                    )
                )
            }
        }

        withType<ShadowJar> {
            mergeServiceFiles()
            archiveFileName.set("${project.name}-${project.version}-all.${archiveExtension.get()}")
        }
    }
}