plugins {
    kotlin("jvm")
}

group = "one.devos"
version = rootProject.version.toString()

repositories {
    mavenCentral()
}

//dependencies {
//    testImplementation(kotlin("test"))
//}
//
//tasks.test {
//    useJUnitPlatform()
//}
kotlin {
    jvmToolchain(21)
}