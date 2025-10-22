plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.0"
}

group = "org.kvxd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven {
        url = uri("https://repo.opencollab.dev/maven-releases")
    }
    maven {
        url = uri("https://repo.opencollab.dev/maven-snapshots")
    }
}

dependencies {
    implementation("org.geysermc.mcprotocollib:protocol:1.21.4-SNAPSHOT")
    implementation("ch.qos.logback:logback-classic:1.5.19")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}