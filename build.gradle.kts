plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

group = "com.eripe14"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.eternalcode.pl/releases")
    maven("https://repo.panda-lang.org/releases")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://jitpack.io")
}

dependencies {
    // -- paper --
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

    // -- adventure --
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("net.kyori:adventure-text-minimessage:4.16.0")
    implementation("net.kyori:adventure-api:4.18.0")

    // -- notifications --
    implementation("com.eternalcode:multification-bukkit:1.1.4")
    implementation("com.eternalcode:multification-okaeri:1.1.4")

    // -- configs --
    implementation("eu.okaeri:okaeri-configs-yaml-bukkit:5.0.6")
    implementation("eu.okaeri:okaeri-configs-serdes-bukkit:5.0.6")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:5.0.6")
    implementation("eu.okaeri:okaeri-configs-json-simple:5.0.6")

    // -- tasker --
    implementation("eu.okaeri:okaeri-tasker-bukkit:2.1.0-beta.3")

    // -- commands --
    implementation("dev.rollczi:litecommands-bukkit:3.9.7")

    // -- database --
    implementation("org.mongodb:mongodb-driver-sync:5.3.1")
    implementation("eu.okaeri:okaeri-persistence-mongo:3.0.1-beta.1")

    // -- lombok --
    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    // -- inventory --
    implementation("xyz.xenondevs.invui:invui:1.44")
    implementation("dev.triumphteam:triumph-gui:3.1.10")

    // -- discord webhook --
    implementation("org.asynchttpclient:async-http-client:3.0.1")

    // -- economy --
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
}

bukkit {
    main = "com.eripe14.marketplace.MarketplacePlugin"
    apiVersion = "1.13"
    prefix = "MarketplacePlugin"
    name = "MarketplacePlugin"
    author = "eripe14"
    version = "${project.version}"
    depend = listOf("Vault")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}

tasks.runServer {
    version("1.21.4")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}