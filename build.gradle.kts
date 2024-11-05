plugins {
    kotlin("jvm") version "2.0.21" apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.26" apply false
    id("org.jetbrains.intellij") version "1.17.4" apply false
}

group = "com.firefinchdev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


extra["kotlinVersion"] = "2.0.21"
extra["kspVersion"] = "2.0.21-1.0.26"
extra["kotlinPoetVersion"] = "2.0.0"

buildscript {
    dependencies {
        classpath(kotlin("gradle-plugin", version = "2.0.21"))
    }
}