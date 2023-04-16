import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "it.unibo.dsdms"

val kotlinxVersion = "1.5.0"


plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("io.vertx.vertx-plugin") version System.getProperty("vertxVersion")
    application
}
vertx.mainVerticle="dsdms.dossier.Server"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

dependencies {
    implementation(kotlin("test"))
    implementation("io.vertx:vertx-web:${System.getProperty("vertxImplVersion")}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxVersion")
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