plugins {
    kotlin("jvm")
}

group = "one.devos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.lavalink.dev/snapshots")
}

dependencies {
//    testImplementation(kotlin("test"))
    implementation(rootProject.libs.lavalink.client)
    implementation(rootProject.libs.lavakord.jda)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}