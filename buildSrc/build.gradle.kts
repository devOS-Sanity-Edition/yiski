plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://gitlab.com/api/v4/projects/26794598/packages/maven") // Aviation GitLab
    maven("https://maven.lavalink.dev/snapshots")
    maven("https://maven.deftu.dev/releases")
    maven("https://mvn.devos.one/snapshots")
}

dependencies {
    implementation(gradleApi())
    implementation("com.gradleup.shadow:shadow-gradle-plugin:8.3.0")
}
