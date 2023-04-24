import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
group = "dsdms.client"
version = "1.0-SNAPSHOT"


@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.vertx)
    alias(libs.plugins.johnrengelman.shadow)

    application
}
application.mainClass.set("dsdms.client.Main")

vertx.mainVerticle = "dsdms.client.Server"

repositories {
    mavenCentral()
}


dependencies {
    implementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.bundles.kotlinx)
    implementation(libs.bundles.vertx.full)

    //Cucumber
    implementation(libs.bundles.cucumber)

    //Allows to add external module classes as dependencies for testing
    implementation(testFixtures(project(":DossierService")))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    manifest {
        attributes["Main-Class"] = "dsdms.client.Main"
    }

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

    destinationDirectory.set(file("$buildDir/output"))
}

