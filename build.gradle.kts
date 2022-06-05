import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.6.21"
    id("io.papermc.paperweight.userdev") version "1.3.6"
    id("xyz.jpenilla.run-paper") version "1.0.6"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
}

group = "de.hglabor"
version = "1.18.2_v1"

val javaVersion = 17 // Minecraft 1.18 requires Java 17

repositories {
    mavenCentral()
}

dependencies {
    paperDevBundle("1.18.2-R0.1-SNAPSHOT")
    compileOnly("net.axay:kspigot:1.18.2")
}

configure<SourceSetContainer> {
    named("main") {
        java.srcDir("src/main/kotlin")
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(javaVersion))
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "$javaVersion"
        }
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(javaVersion)
    }
}


bukkit {
    name = "maze-generator"
    apiVersion = "1.18"
    authors = listOf(
        "Your Name",
    )
    main = "$group.mazegenerator.MazeGenerator"
    version = getVersion().toString()
    libraries = listOf(
        "net.axay:kspigot:1.18.2",
    )
}
