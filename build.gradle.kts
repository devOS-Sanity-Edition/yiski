plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.ktor)
//    alias(libs.plugins.shadow)
    java
}

group = "one.devos"
version = "0.1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    (1..6).forEach { module ->
        implementation(project(":yiski$module"))
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "application")
    apply(plugin = "io.ktor.plugin")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://gitlab.com/api/v4/projects/26794598/packages/maven") // Aviation GitLab
    }

    dependencies {
        if (project.name != "yiski-common" && !project.name.startsWith("yiski-module"))
            implementation(project(":yiski-common"))

        if (!project.name.startsWith("yiski-module")) {
            implementation(project(":yiski-module-metadata"))
            implementation(project(":yiski-module-loader"))
        }

        api(rootProject.libs.slf4j.api)
        implementation(kotlin("reflect"))
        implementation(rootProject.libs.bundles.exposed)
        implementation(rootProject.libs.bundles.jda) {
            exclude(module = "open-java")
        }
        implementation(rootProject.libs.bundles.ktor)
        implementation(rootProject.libs.bundles.ktoml)
        implementation(rootProject.libs.bundles.kotlin)
        implementation(rootProject.libs.bundles.kotlinx)
        implementation(rootProject.libs.bundles.logback)
        implementation(rootProject.libs.postgresql)
        implementation(rootProject.libs.kotlin.logging)
        implementation(rootProject.libs.reflection)
    }

    // Write the version to the fabric.mod.json
    tasks.processResources {
        inputs.property("version", project.version)

        filesMatching("yiski.metadata.toml") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    application {
        mainClass.set("one.devos.yiski.runner.YiskiRunner")
    }

    ktor {
        fatJar {
            archiveFileName.set("Yiski.jar")
        }
    }
}