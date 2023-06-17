plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)
    application
    alias(libs.plugins.dokka)

    id("java-library")
}
application.mainClass.set("dsdms.doctor.Main")

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.vertx.server)
    implementation(libs.bundles.kmongo)
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    manifest.attributes["Main-Class"] = application.mainClass
    val projectVersion = project.properties["version"] as String
    archiveFileName.set("${project.name}-$projectVersion.jar")
    destinationDirectory.set(file("$buildDir/output"))
}

tasks.register<Jar>("createJavadoc") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}
