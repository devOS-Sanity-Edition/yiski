import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    id("one.devos.yiski.build")
    alias(libs.plugins.ktor)
    java
}

group = "one.devos"
version = System.getenv("GITHUB_SHA") ?: "Development"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    api("shade"(project(":yiski-dependencies"))!!)
    "shade"(project(":yiski-common"))
    "shade"(project(":yiski-module-metadata"))
    "shade"(project(":yiski-module-loader"))

    (1..6).forEach { module ->
        runtimeOnly(project(":yiski$module"))
    }
}

kotlin {
    jvmToolchain(21)
}

tasks {

    jar {
        manifest {
            attributes["Main-Class"] = "one.devos.yiski.runner.YiskiRunner"
        }
    }

    fatJar {
        archiveFileName.set("Yiski-$version.jar")
    }

    test {
        useJUnitPlatform()
    }

}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "io.ktor.plugin")
    apply(plugin = "com.github.johnrengelman.shadow")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://gitlab.com/api/v4/projects/26794598/packages/maven") // Aviation GitLab
        maven("https://maven.lavalink.dev/snapshots")
        maven("https://maven.deftu.dev/releases")
        maven("https://mvn.devos.one/snapshots")
    }

    dependencies {
        if (project.name != "yiski-dependencies") {
            if (project.name != "yiski-common" && !project.name.startsWith("yiski-module")) {
                implementation(project(":yiski-common"))
            }

            if (!project.name.startsWith("yiski-module")) {
                implementation(project(":yiski-module-metadata"))
                implementation(project(":yiski-module-loader"))
            }
        }
    }

    // Write the version to the yiski.metadata.toml
    tasks.processResources {
        inputs.property("version", project.version)

        filesMatching("yiski.metadata.toml") {
            expand(mutableMapOf("version" to project.version))
        }
    }
}

subprojects {
    dependencies {
        if (project.name != "yiski-dependencies") {
            implementation(project(":yiski-dependencies"))
        }
    }
}
