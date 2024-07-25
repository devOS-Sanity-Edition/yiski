plugins {
    kotlin("jvm")
}

group = "one.devos"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    testImplementation(kotlin("test"))
    implementation(rootProject.libs.github.api)

}
//
//tasks.test {
//    useJUnitPlatform()
//}
kotlin {
    jvmToolchain(21)
}