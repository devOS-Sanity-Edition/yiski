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

    implementation(files("libs/osu4j-2.0.1.jar"))
    shade(files("libs/osu4j-2.0.1.jar"))
}

kotlin {
    jvmToolchain(21)
}
