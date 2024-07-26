plugins {
    kotlin("jvm")
//    alias(libs.plugins.shadow)
}

group = "one.devos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation(kotlin("test"))
}

//tasks.test {
//    useJUnitPlatform()
//}
kotlin {
    jvmToolchain(21)
}