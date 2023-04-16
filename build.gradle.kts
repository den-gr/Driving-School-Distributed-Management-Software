import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("io.vertx.vertx-plugin") version "1.4.0"
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("se.thinkcode.cucumber-runner") version "0.0.11"
    id("org.danilopianini.gradle-kotlin-qa") version "0.19.1"
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
    kotlinOptions.jvmTarget = "16"
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.vertx:vertx-web:4.4.1")
    implementation("io.vertx:vertx-web-client:4.4.1")
    testImplementation("io.cucumber:cucumber-java:7.11.2")
    testImplementation("io.cucumber:cucumber-junit:7.11.2")
    testImplementation("io.cucumber:cucumber-java8:7.11.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.2")

}

tasks.test {
    useJUnitPlatform()
}


application {
    mainClass.set("dsdms.client.Main")
}

tasks.register<Exec>("myRun") {//inline function with reified type!
    //Configuration action is of type T.() -> Unit, in this case Exec.T() -> Unit
    val javaExecutable = org.gradle.internal.jvm.Jvm.current().javaExecutable.absolutePath
    commandLine( // this is a method of class org.gradle.api.Exec
        javaExecutable, "-version"
    )
    //There is no need of doLast / doFirst, actions are already configured
    //Still, we may want to do something before or after the task has been executed
    doLast { println("$javaExecutable invocation complete") }
    doFirst { println("Ready to invoke $javaExecutable") }
}


