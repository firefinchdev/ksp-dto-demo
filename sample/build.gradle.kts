plugins {
    kotlin("jvm") version "2.0.21"
    id("com.google.devtools.ksp")
}

group = "com.firefinchdev.sample"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":generator-lib"))
    ksp(project(":generator-lib"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}