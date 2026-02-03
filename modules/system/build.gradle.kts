plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    api(libs.bundles.moeka) {
        exclude(module = "kord-core")
    }
    implementation(libs.discord.kord.core)
}
