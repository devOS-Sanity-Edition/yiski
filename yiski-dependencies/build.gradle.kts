repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://gitlab.com/api/v4/projects/26794598/packages/maven") // Aviation GitLab
    maven("https://maven.lavalink.dev/snapshots")
    maven("https://maven.deftu.dev/releases")
    maven("https://mvn.devos.one/snapshots")
}

dependencies {
    api(kotlin("reflect"))
    api(rootProject.libs.bundles.exposed)
    api(rootProject.libs.bundles.jda) {
        exclude(module = "open-java")
    }

    api(rootProject.libs.slf4j.api)
    api(rootProject.libs.bundles.ktor)
    api(rootProject.libs.bundles.ktoml)
    api(rootProject.libs.bundles.kotlin)
    api(rootProject.libs.bundles.kotlinx)
    api(rootProject.libs.bundles.lavalink)
    api(rootProject.libs.bundles.logback)
    api(rootProject.libs.postgresql)
    api(rootProject.libs.kotlin.logging)
    api(rootProject.libs.reflection)
}
