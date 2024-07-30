plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kmp) apply false
}

group = "com.github.tywinlanni.hydra"
version = "0.0.1"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }
}

tasks {
    arrayOf("build", "clean", "check").forEach { tsk ->
        create(tsk) {
            group = "build"
            dependsOn(subprojects.map { it.getTasksByName(tsk,false) })
        }
    }
}
