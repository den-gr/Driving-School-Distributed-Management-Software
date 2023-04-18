group = "dsdms.client"
version = "1.0-SNAPSHOT"


@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vertx)
    alias(libs.plugins.johnrengelman.shadow)

    //service plugins
    alias(libs.plugins.cucumber.runner)
}
vertx.mainVerticle="dsdms.client.Server"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))

    //Kotlin utils
    implementation(libs.bundles.kotlinx)

    //Vertx
    implementation(libs.bundles.vertx.full)

    //Cucumber
    testImplementation(libs.bundles.cucumber)
    testRuntimeOnly(libs.junit.vintage)

    //Allows to add external module classes as dependencies for testing
    testImplementation(testFixtures(project(":DossierService")))
}

tasks.test {
    useJUnitPlatform()
}