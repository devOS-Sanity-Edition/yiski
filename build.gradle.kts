plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.serialization") version "2.0.0"
    alias(libs.plugins.ktor)
    java
}

group = "one.devos"
version = "1.0-SNAPSHOT"

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
        if (project.name != "yiski-common")
            implementation(project(":yiski-common"))
        api(rootProject.libs.slf4j.api)
        implementation(kotlin("reflect"))
        implementation(rootProject.libs.bundles.exposed)
        implementation(rootProject.libs.bundles.jda)
        implementation(rootProject.libs.bundles.ktor)
        implementation(rootProject.libs.bundles.ktoml)
        implementation(rootProject.libs.bundles.kotlin)
        implementation(rootProject.libs.bundles.kotlinx)
        implementation(rootProject.libs.bundles.logback)
        implementation(rootProject.libs.kotlin.logging)
        implementation(rootProject.libs.reflection)
    }

    application {
        mainClass.set("one.devos.yiski.Yiski")
    }

    ktor {
        fatJar {
            archiveFileName.set("Yiski.jar")
        }
    }
}