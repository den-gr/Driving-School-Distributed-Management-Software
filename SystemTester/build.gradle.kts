import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val cucumberVersion = "7.11.2"

plugins {
    kotlin("jvm") // version "1.7.10"
    kotlin("plugin.serialization") // version "1.7.10"
    id("io.vertx.vertx-plugin") // version "1.4.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("se.thinkcode.cucumber-runner") version "0.0.11"
}
vertx.mainVerticle="dsdms.client.Server"

repositories {
    mavenCentral()
}

allprojects{
    group = "dsdms"
    version = "1.0-SNAPSHOT"

}

subprojects{

}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.vertx:vertx-web:4.4.1")
    implementation("io.vertx:vertx-web-client:4.4.1")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
    testImplementation("io.cucumber:cucumber-java8:$cucumberVersion")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")
    implementation(kotlin("stdlib-jdk8"))

}

tasks.test {
    useJUnitPlatform()
}
