plugins {
    id("java")
    id("com.gradleup.shadow") version "9.6.1"
    id("xyz.jpenilla.run-paper") version "3.1.0"
}

group = "org.elh0mbr3"
version = "0.1"
description = "NPC engine"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")

    compileOnly("com.comphenix.protocol:ProtocolLib:5.3.0")
    implementation("it.unimi.dsi:fastutil:8.5.12")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        relocate("it.unimi.dsi.fastutil", "org.elh0mbr3.coreNPC.libs.fastutil")
    }

    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.20.6")
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    processResources {
        val props = mapOf("version" to version , "description" to project.description )

        inputs.properties(props)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
