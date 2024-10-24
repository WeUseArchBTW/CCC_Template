plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.github.weusearchbtw"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // histogramm diff
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.7.0.202309050840-r")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
