import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("java-library")
    id("io.github.goooler.shadow") version "8.1.8"
}

group = "github.xCykrix"
version = "1.0-0-SNAPSHOT"

repositories {
    mavenCentral()

    // Core Repositories
    maven("https://jitpack.io/")
    maven("https://oss.sonatype.org/content/groups/public/")

    // Spigot Repository
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

    // API Repositories
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    // Spigot-based API
    compileOnly("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT")

    // Bundled Libraries
    implementation("org.apache.commons:commons-lang3:3.15.0")
    implementation("net.kyori:adventure-api:4.17.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
    implementation("dev.dejvokep:boosted-yaml-spigot:1.4")
    implementation("dev.jorel:commandapi-bukkit-shade:9.5.2")
    implementation("com.h2database:h2-mvstore:2.3.232")

    // APIs
    api("com.comphenix.protocol:ProtocolLib:5.1.0");
}

tasks.withType<ShadowJar> {
    relocate("dev.dejvokep", "github.xCykrix.shade.dev.dejvokep")
    relocate("dev.jorel", "github.xCykrix.shade.dev.jorel")
    relocate("org.h2.mvstore", "github.xCykrix.shade.org.h2.mvstore")
}

// Target Java Build (Java 17 - Minecraft 1.17.x)
val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}