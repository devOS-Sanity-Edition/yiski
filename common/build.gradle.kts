dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.ktor.client)
    implementation(libs.ktor.shared.serialization.kotlinx.json)
}

tasks {
    val propsTask = register<WriteProperties>("yiskiProperties") {
        destinationFile = layout.buildDirectory.file("yiski.properties")
        encoding = "UTF-8"

        property("git.hash", gitVersioning.gitVersionDetails.commit)

        property("versions.kord", libs.versions.kord.get())
        property("versions.moeka", libs.versions.moeka.get())
        property("versions.yiski", project.version.toString())
    }

    processResources {
        from(propsTask) {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}