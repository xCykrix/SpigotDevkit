plugins {
    id("com.gradleup.shadow") version "8.3.0"
    id("java")
    id("java-library")
}

group = "github.xCykrix"
version = "1.0-0-SNAPSHOT"

repositories {
    mavenCentral()

    // Core Repositories
    maven {
        url = uri("https://jitpack.io/")
    }
    maven {
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }

    // Spigot Repository
    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    // API Repositories
    maven {
        name = "dmulloy2"
        description = "ProtocolLib"
        url = uri("https://repo.dmulloy2.net/repository/public/")
    }
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
    implementation("com.h2database:h2-mvstore:2.3.232")

    // APIs
    api("com.comphenix.protocol:ProtocolLib:5.1.0");
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