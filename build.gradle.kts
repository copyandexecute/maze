val javaVersion = 17 // Minecraft 1.18 requires Java 17

plugins {
    kotlin("jvm") version "1.6.21"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    `java-library`
}

val mcVersion = "1.17.1"
group = "de.hglabor"
version = "${mcVersion}_v1"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("org.bukkit:craftbukkit:${mcVersion}-R0.1-SNAPSHOT") //bitte lasst mich in ruhe ganz traurig
    compileOnly("net.axay:kspigot:1.17.4")
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "$javaVersion"
        }
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(javaVersion)
    }
}


bukkit {
    name = "maze-generator"
    apiVersion = "1.17"
    authors = listOf(
        "Your Name",
    )
    main = "$group.mazegenerator.MazeGenerator"
    version = getVersion().toString()
    libraries = listOf(
        "net.axay:kspigot:1.17.4",
    )
}
