plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(projects.twHydraCommon)
    implementation(projects.twHydraRepoCommon)

    implementation(libs.mongo.driver)
    implementation(libs.mongo.bson.kotlinx)

    implementation(libs.coroutines.core)
    implementation(libs.uuid)

    testImplementation(projects.twHydraRepoTests)
    testImplementation(libs.testcontainers.mongodb)
}
