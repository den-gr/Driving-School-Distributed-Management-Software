group = "dsdms.dossier"
version = "0.0.1"

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vertx)
    alias(libs.plugins.johnrengelman.shadow)
    application

    id("java-library")
    id("io.kotest.multiplatform") version "5.0.2"
}
vertx.mainVerticle="dsdms.dossier.Main" //TODO

dependencies {
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.vertx.server)
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.kmongo)

    implementation("io.kotest:kotest-framework-engine:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest:kotest-property:5.5.5")
}

repositories {
    mavenCentral()
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "dsdms.dossier.Main"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    destinationDirectory.set(file("$buildDir/output"))
}

application {
    mainClass.set("dsdms.dossier.Main")
}