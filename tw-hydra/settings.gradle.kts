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
include(":tw-hydra-api-v1-kmp")
include(":tw-hydra-common")
include(":tw-hydra-app-ktor")
include(":tw-hydra-stubs")
include(":tw-hydra-app-common")
include(":tw-hydra-biz")
include(":tw-hydra-repo-common")
include(":tw-hydra-repo-inmemory")
include(":tw-hydra-repo-stubs")
include(":tw-hydra-repo-tests")
include(":tw-hydra-repo-mongo")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
