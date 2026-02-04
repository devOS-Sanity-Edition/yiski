plugins {
    alias(libs.plugins.ktor)
}

dependencies {
    api(libs.bundles.moeka) {
        exclude(module = "kord-core")
    }
    implementation(libs.discord.kord.core)

    implementation(libs.bundles.ktor.client)
    implementation(libs.bundles.ktor.server)
    implementation(libs.ktor.shared.serialization.kotlinx.json)
}

application {
    mainClass.set("${project.group}.${project.name}.MainKt")
}