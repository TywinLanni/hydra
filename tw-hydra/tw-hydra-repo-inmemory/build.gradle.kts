plugins {
    id("build-kmp")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(projects.twHydraCommon)
                api(projects.twHydraRepoCommon)

                implementation(libs.coroutines.core)
                implementation(libs.coroutines.test)
                implementation(libs.db.cache4k)
                implementation(libs.uuid)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(projects.twHydraRepoTests)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
