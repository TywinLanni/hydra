dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

pluginManagement {
    includeBuild("../build-plugin")
    plugins {
        id("build-jvm") apply false
        id("build-kmp") apply false
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
    }
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "tw-hydra"

include(":test-module")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")