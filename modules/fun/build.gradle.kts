plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(libs.bundles.moeka) {
        exclude(module = "kord-core")
    }
    implementation(libs.discord.kord.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.ksoup)
    implementation(libs.ksoup.network.ktor)
}
