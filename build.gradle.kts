plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.serialization") version "1.5.31"
}

group = "net.ciber"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

dependencies {
    implementation("org.spigotmc:spigot:1.8.8-R0.1-SNAPSHOT")

    implementation("io.insert-koin:koin-core:3.1.3")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    implementation("com.charleskorn.kaml:kaml:0.37.0")
}

val sources by tasks.registering(Jar::class) {
    archiveBaseName.set(project.name)
    archiveClassifier.set("sources")
    archiveVersion.set(project.version.toString())
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(sources.get())
        }
    }
}

tasks.jar {
    destinationDirectory.set(file("C:\\Users\\tulio\\Documents\\Projetos\\Minecraft\\Server\\1.8.8\\plugins"))
}