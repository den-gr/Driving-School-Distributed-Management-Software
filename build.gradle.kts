import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    id("io.vertx.vertx-plugin") version "1.4.0"
    application
}
vertx.mainVerticle="dsdms.client.Client"

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
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.vertx:vertx-web:4.4.1")
    implementation("io.vertx:vertx-web-client:4.4.1")
    testImplementation("io.cucumber:cucumber-java:7.11.2")
    testImplementation("io.cucumber:cucumber-junit:7.11.2")
    testImplementation("io.cucumber:cucumber-java8:7.11.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")

}

tasks.test {
    useJUnitPlatform()
}




application {
    mainClass.set("dsdms.client.Main")
}