
group = "it.unibo.dsdms.dossier"
version = "0.0.1"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)
    alias(libs.plugins.kotest.multiplatform)
    application

    id("java-library")
}
application.mainClass.set("dsdms.dossier.Main")

dependencies {
    testImplementation(libs.bundles.kotest)
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.kmongo)
    implementation(libs.bundles.vertx.server)
}


tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    manifest.attributes["Main-Class"] = application.mainClass
    archiveFileName.set("${project.name}-${project.version}.jar")
    destinationDirectory.set(file("$buildDir/output"))
}