plugins {
    kotlin("jvm")
    id("one.devos.yiski.build")
}

group = "one.devos"
version = rootProject.version.toString()

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
