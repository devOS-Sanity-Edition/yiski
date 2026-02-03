plugins {
    alias(libs.plugins.ktor)
}

dependencies {
    api(libs.bundles.moeka)

    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.shared.serialization.kotlinx.json)
}

application {
    mainClass.set("${project.group}.${project.name}.MainKt")
}