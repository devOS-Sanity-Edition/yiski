plugins {
    kotlin("jvm")
}

group = "one.devos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://mvn.devos.one/snapshots")
}

dependencies {
//    testImplementation(kotlin("test"))
    implementation(libs.yiski.shared.data)
}
//
//tasks.test {
//    useJUnitPlatform()
//}
kotlin {
    jvmToolchain(21)
}