plugins {
    application
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(project(":common"))

    implementation(libs.bundles.logging)

    implementation(libs.clikt)

    implementation(libs.discord.kord.core)
    implementation(libs.bundles.moeka) {
        exclude(module = "kord-core")
    }

    implementation(libs.bundles.exposed)
    implementation(libs.exposed.driver.postgresql)

    implementation(libs.github.api)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    runtimeOnly(libs.kotlin.scripting.jsr223)

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.reflections)

    implementation(libs.stacktrace.decoroutinator)

    implementation(project(":modules:system"))

    // Modules - dynamically loaded because this bot is a clusterf**k
    file("../modules").listFiles()?.forEach { file ->
        if (file.isDirectory && file.resolve("build.gradle.kts").exists()) {
            api(project(":modules:${file.name}"))
        }
    }
}

application {
    mainClass.set("${project.group}.yiski.MainKt")
}