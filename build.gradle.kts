group = "dsdms"
version = "1.0-SNAPSHOT"

val kotlinxVersion = "1.5.0"

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("io.vertx.vertx-plugin") version System.getProperty("vertxVersion")
    id("com.github.johnrengelman.shadow") version "7.0.0"
}
vertx.mainVerticle="dsdms.dossier.Main" //TODO

repositories {
    mavenCentral()
}


subprojects{
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "16"
    }


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


