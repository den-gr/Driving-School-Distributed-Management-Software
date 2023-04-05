import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
group = "it.unibo.dsdms"

plugins {
    kotlin("jvm") version "1.8.20"
    application
    java
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

repositories {
    mavenCentral()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "it.unibo.dsdms.dossier.MyClass"
    }
}