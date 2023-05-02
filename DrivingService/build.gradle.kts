group = "dsdms.driving"
version = "0.0.1"

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vertx)
    alias(libs.plugins.johnrengelman.shadow)
    alias(libs.plugins.kotest.multiplatform)
    application

    id("java-library")
}

vertx.mainVerticle="dsdms.driving.Main" //TODO

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.kmongo)
    implementation(libs.bundles.vertx.server)
    implementation(libs.bundles.kotest)
}

repositories {
    mavenCentral()
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "dsdms.driving.Main"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    destinationDirectory.set(file("$buildDir/output"))
}

application {
    mainClass.set("dsdms.driving.Main")
}