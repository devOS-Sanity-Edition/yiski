dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.lizainslie.dev/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://mvn.devos.one/releases")
        maven("https://mvn.devos.one/snapshots")
    }
}

rootProject.name = "yiski"

// Bot core project
include(":bot")

// Commonly reused
include(":common")

// Modules - dynamically loaded because this bot is a clusterf**k
file("modules").listFiles()?.forEach { file ->
    if (file.isDirectory && file.resolve("build.gradle.kts").exists()) {
        include(":modules:${file.name}")
    }
}