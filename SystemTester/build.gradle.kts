import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "dsdms.client"
version = "1.0-SNAPSHOT"

val cucumberVersion = "7.11.2"
val kotlinxVersion = "1.5.0"

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("io.vertx.vertx-plugin")
    id("com.github.johnrengelman.shadow")

    //service plugins
    id("se.thinkcode.cucumber-runner") version "0.0.11" //add gradle task for running cucumber
}
vertx.mainVerticle="dsdms.client.Server"

repositories {
    mavenCentral()
}


dependencies {
    val vertxImplVersion: String by System.getProperties()
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))

    //Vertx
    implementation("io.vertx:vertx-web:$vertxImplVersion")
    implementation("io.vertx:vertx-web-client:$vertxImplVersion")
    implementation("io.netty:netty-all:4.1.90.Final") // fix macOS vertx warning

    //Cucumber
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-java8:$cucumberVersion")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")

    //Kotlin utils
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxVersion")

    //Allows to add external module classes as dependencies for testing
    testImplementation(testFixtures(project(":DossierService")))
}

tasks.test {
    useJUnitPlatform()
}