group = "dsdms.dossier"
version = "0.0.1"

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vertx)
    alias(libs.plugins.johnrengelman.shadow)
    application

    //allows export module classes as test dependencies
    id("java-test-fixtures")
}
vertx.mainVerticle="dsdms.dossier.Main" //TODO

dependencies {
    implementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.vertx.server)
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.kmongo)

//    implementation("ch.qos.logback:logback-classic:1.2.11")
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
    destinationDirectory.set(file("$buildDir/output"))
}

application {
    mainClass.set("dsdms.dossier.Main")
}