import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "it.unibo.dsdms"
version = "0.0.1-SNAPSHOT1"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)

    signing
    `maven-publish`
    id("org.jetbrains.dokka") version "1.7.20"
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"

}

allprojects{
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }

    repositories {
        mavenCentral()
    }
}