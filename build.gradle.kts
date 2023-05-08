import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "it.unibo.dsdms.client"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)

//    id("org.jlleitschuh.gradle.ktlint") version "11.3.2" //TEMPORALLY DISABLE
}

allprojects {
//    apply(plugin = "org.jlleitschuh.gradle.ktlint") //TEMPORALLY DISABLE

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }

    repositories {
        mavenCentral()
    }
}
