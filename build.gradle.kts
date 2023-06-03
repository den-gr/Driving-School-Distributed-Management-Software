import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "it.unibo.dsdms.client"

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)
    alias(libs.plugins.dokka)
    id("org.danilopianini.gradle-kotlin-qa") version "0.42.0"

//    id("org.jlleitschuh.gradle.ktlint") version "11.3.2" //TEMPORALLY DISABLE
}

allprojects {
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.danilopianini.gradle-kotlin-qa")

    detekt {
        config.setFrom("../detekt.yml")
    }

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

    tasks.withType<Jar> {
        archiveClassifier.set("sources")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
