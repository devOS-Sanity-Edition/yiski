plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "yiski"

// Module loader
include("yiski-module-metadata")
include("yiski-module-loader")

// Modules
include("yiski-common")

(1..6).forEach { module ->
    include(":yiski$module")
}
