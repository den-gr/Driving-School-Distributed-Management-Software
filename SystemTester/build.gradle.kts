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
    id("se.thinkcode.cucumber-runner") version "0.0.11"
}
vertx.mainVerticle="dsdms.client.Server"

repositories {
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

dependencies {
    val vertxImplVersion: String by System.getProperties()
    testImplementation(kotlin("test"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.vertx:vertx-web:$vertxImplVersion")
    implementation("io.vertx:vertx-web-client:$vertxImplVersion")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-java8:$cucumberVersion")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxVersion")
    implementation("io.netty:netty-all:4.1.90.Final")

    testImplementation(testFixtures(project(":DossierService")))
}

tasks.test {
    useJUnitPlatform()
}