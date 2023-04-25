group = "dsdms.client"
version = "0.0.1"


@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)

    application

    id("io.ktor.plugin") version "2.3.0"
}


application.mainClass.set("dsdms.client.Main")

repositories {
    mavenCentral()
}


dependencies {
    implementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.vertx.client)

    //Cucumber
    implementation(libs.bundles.cucumber)

    //Allows to add external module classes as dependencies for testing
    implementation(project(":DossierService"))

}

tasks.test {
    useJUnitPlatform()
}


tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "dsdms.client.Main"
    }
    archiveFileName.set("${project.name}-${project.version}.jar")
    destinationDirectory.set(file("$buildDir/output"))
}

