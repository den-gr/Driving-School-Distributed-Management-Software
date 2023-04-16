import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "it.unibo.dsdms"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.vertx.vertx-plugin")
    application
}
vertx.mainVerticle="dsdms.dossier.Server"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.vertx:vertx-web:4.4.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
}

repositories {
    mavenCentral()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "dsdms.dossier.Main"
    }
}

application {
    mainClass.set("dsdms.dossier.Main")
}