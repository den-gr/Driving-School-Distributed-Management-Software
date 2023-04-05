import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects{
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

plugins {
    kotlin("jvm") version "1.8.20"
    application
}

group = "it.unibo.dsdms"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}



application {
    mainClass.set("MainKt")
}