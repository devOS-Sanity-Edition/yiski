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
    implementation("io.ktor:ktor-client-core-jvm:3.0.1")
    implementation("io.ktor:ktor-client-apache:3.0.1")
    implementation("io.ktor:ktor-client-cio-jvm:3.0.1")
    shade(rootProject.libs.github.api)

    implementation(rootProject.libs.okhttp)
    shade(rootProject.libs.okhttp)

    implementation(rootProject.libs.ktor.client.core)
    shade(rootProject.libs.ktor.client.core)

    implementation(rootProject.libs.ktor.client.cio.jvm)
    shade(rootProject.libs.ktor.client.cio.jvm)

    implementation(rootProject.libs.ksoup)
    shade(rootProject.libs.ksoup)

    implementation(files("libs/osu4j-2.0.1.jar"))
    shade(files("libs/osu4j-2.0.1.jar"))
}

kotlin {
    jvmToolchain(21)
}
