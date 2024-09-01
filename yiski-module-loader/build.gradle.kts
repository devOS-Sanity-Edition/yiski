plugins {
    kotlin("jvm")
}

group = "one.devos"
version = rootProject.version.toString()

dependencies {
    implementation(project(":yiski-module-metadata"))
}

kotlin {
    jvmToolchain(21)
}
