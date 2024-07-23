plugins {
    kotlin("jvm")
}

group = "one.devos"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":yiski-module-metadata"))
}

kotlin {
    jvmToolchain(21)
}
