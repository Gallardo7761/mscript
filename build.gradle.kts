plugins {
    kotlin("jvm") version "2.4.10"
    `maven-publish`
}

group = "net.miarma"
version = "26.7.1"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:[26.1.2.build.1, 26.1.2.build.9999)")
    compileOnly("de.tr7zw:item-nbt-api-plugin:2.15.3-SNAPSHOT")
    implementation("dev.dejvokep:boosted-yaml:1.3.7")
}

kotlin {
    jvmToolchain(25)
}

tasks {
    build {
        dependsOn("jar")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "MiarmaGit"
            url = uri("https://git.miarma.net/api/packages/Gallardo7761/maven")

            credentials {
                username = System.getenv("GITEA_USER") ?: project.findProperty("giteaUser") as String?
                password = System.getenv("GITEA_TOKEN") ?: project.findProperty("giteaToken") as String?
            }
        }
    }
}