import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
group = "it.unibo.dsdms"

plugins {
    kotlin("jvm")
    id("io.vertx.vertx-plugin")
    application
}
vertx.mainVerticle="dsdms.dossier.Server"

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.vertx:vertx-web:4.4.1")
}

repositories {
    mavenCentral()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "it.unibo.dsdms.dossier.MyClass"
    }
}

application {
    mainClass.set("dsdms.dossier.MainClass")
}