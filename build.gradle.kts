import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.0"
}

group = "com.coditory.sandbox"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

ktlint {
    version.set("0.38.1")
}

object Versions {
    const val junit = "5.6.2"
    const val jackson = "2.11.2"
    const val kotlin = "1.4.0"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${Versions.jackson}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.jackson}")
    testImplementation("org.skyscreamer:jsonassert:1.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junit}")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        setExceptionFormat("full")
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "12"
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}
