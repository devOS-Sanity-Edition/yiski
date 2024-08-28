plugins {
    kotlin("jvm")
    id("one.devos.yiski.build")
}

group = "one.devos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(rootProject.libs.github.api)
    shade(rootProject.libs.github.api)

    implementation(rootProject.libs.okhttp)
    shade(rootProject.libs.okhttp)
}

kotlin {
    jvmToolchain(21)
}
