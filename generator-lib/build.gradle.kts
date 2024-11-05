plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.firefinchdev.generators"
version = "1.0-SNAPSHOT"

val kotlinVersion: String by rootProject.extra
val kspVersion: String by rootProject.extra
val kotlinPoetVersion: String by rootProject.extra

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:$kspVersion")

    implementation(kotlin("reflect"))

    implementation("com.squareup:kotlinpoet:$kotlinPoetVersion")
    implementation("com.squareup:kotlinpoet-ksp:$kotlinPoetVersion")

    testImplementation(kotlin("test"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}