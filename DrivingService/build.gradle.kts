group = "it.unibo.dsdms.driving"
version = "0.0.1"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)
//    application
}


dependencies {
    testImplementation(libs.bundles.kotest)
    implementation(kotlin("stdlib-jdk8"))
}