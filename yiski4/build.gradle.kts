plugins {
    kotlin("jvm")
}

group = "one.devos"
version = rootProject.version.toString()

repositories {
    mavenCentral()
    maven("https://mvn.devos.one/snapshots")
}

dependencies {
//    testImplementation(kotlin("test"))
    implementation(rootProject.libs.yiski.shared.data)
}
//
//tasks.test {
//    useJUnitPlatform()
//}
kotlin {
    jvmToolchain(21)
}