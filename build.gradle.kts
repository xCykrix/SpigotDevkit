import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("com.gradleup.shadow") version "8.3.5"
}

// Variables
group = "github.xCykrix"
version = "1.0.7"

// Repositories
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

// Dependencies
dependencies {
    // Spigot-based API
    compileOnly("org.spigotmc:spigot-api:1.21.5-R0.1-SNAPSHOT")

    // Bundled Libraries
    implementation("org.apache.commons:commons-lang3:3.17.0")
    implementation("net.kyori:adventure-api:4.20.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-text-minimessage:4.20.0")
    implementation("dev.dejvokep:boosted-yaml-spigot:1.4")
    implementation("dev.jorel:commandapi-bukkit-shade:10.0.0")
    implementation("com.h2database:h2-mvstore:2.3.232")

    // APIs
    compileOnly("com.comphenix.protocol:ProtocolLib:5.3.0")
}

// Shadow Task
tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier = null
    isEnableRelocation = true
    relocationPrefix = "dist.xCykrix.shade"
}

// Publications
publishing {
    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/xCykrix/SpigotDevkit")
            credentials {
            username = project.findProperty("GITHUB_ACTOR").toString() ?: System.getenv("GITHUB_ACTOR")
            password = project.findProperty("GITHUB_TOKEN").toString() ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

// Target Java Build (Java 16 - Minecraft 1.17.x)
val targetJavaVersion = 16
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}
