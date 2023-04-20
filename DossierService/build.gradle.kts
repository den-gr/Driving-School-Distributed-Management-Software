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
    id("io.kotest.multiplatform") version "5.0.2"
}
vertx.mainVerticle="dsdms.dossier.Main" //TODO

dependencies {
    implementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.vertx.server)
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.kmongo)

    implementation("io.kotest:kotest-framework-engine:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest:kotest-property:5.5.5")

    implementation("org.litote.kmongo:kmongo-id-serialization:4.9.0")

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
    destinationDirectory.set(file("$buildDir/outputJar"))
}

application {
    mainClass.set("dsdms.dossier.Main")
}