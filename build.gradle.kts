import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "it.unibo.dsdms.client"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)
    alias(libs.plugins.dokka)

//    id("org.jlleitschuh.gradle.ktlint") version "11.3.2" //TEMPORALLY DISABLE
}

allprojects {
//    apply(plugin = "org.jlleitschuh.gradle.ktlint") //TEMPORALLY DISABLE
    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }

    tasks.register<Jar>("createJavadoc") {
        from(tasks.dokkaJavadoc.get().outputDirectory)
        archiveClassifier.set("javadoc")
    }

    repositories {
        mavenCentral()
    }

    tasks.withType<Jar>{
        archiveClassifier.set("sources")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}