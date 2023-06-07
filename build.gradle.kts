import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.johnrengelman.shadow)
    alias(libs.plugins.dokka)
    alias(libs.plugins.qa)
}

val javaVersion = JavaVersion.VERSION_16.toString()

allprojects {
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "org.danilopianini.gradle-kotlin-qa")

    detekt {
        config.setFrom("../detekt.yml")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = javaVersion
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
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
