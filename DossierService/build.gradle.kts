import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dsdms.dossier"
version = "0.0.1"

val kotlinxVersion = "1.5.0"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.vertx.vertx-plugin")
    id("com.github.johnrengelman.shadow")
    application

    //allows export module classes as test dependencies
    id("java-test-fixtures")
}
vertx.mainVerticle="dsdms.dossier.Main" //TODO

dependencies {
    implementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))

    //Vertx
    implementation("io.vertx:vertx-web:${System.getProperty("vertxImplVersion")}")
    implementation("io.netty:netty-all:4.1.90.Final")

    //Kotlin utils
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxVersion")
}

repositories {
    mavenCentral()
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "dsdms.dossier.Main"
    }

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
    destinationDirectory.set(file("$buildDir/outputJar"))
}

application {
    mainClass.set("dsdms.dossier.Main")
}