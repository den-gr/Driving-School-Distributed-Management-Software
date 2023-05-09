group = "it.unibo.dsdms.client"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)

    application
}
application.mainClass.set("dsdms.client.Main")

dependencies {
    implementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.vertx.client)
    implementation(libs.bundles.cucumber)

    // Allows to add external module classes as dependencies
    implementation(project(":DossierService"))
    implementation(project(":DrivingService"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar{
    archiveClassifier.set("sources")
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    manifest.attributes["Main-Class"] = application.mainClass
    val projectVersion = project.properties["version"] as String
    archiveFileName.set("${project.name}-$projectVersion.jar")
    destinationDirectory.set(file("$buildDir/output"))
}
