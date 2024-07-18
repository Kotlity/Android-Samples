
val kotlin_version: String by project
val logback_version: String by project
val mongo_version: String by project
val koin_version: String by project

plugins {
    kotlin("jvm") version "2.0.0"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")

    // kotlinx.serialization
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")

    // Content Negotiation
    implementation("io.ktor:ktor-server-content-negotiation-jvm")

    // MongoDB
    implementation("org.mongodb:mongodb-driver-core:$mongo_version")
    implementation("org.mongodb:mongodb-driver-sync:$mongo_version")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:$mongo_version")
    implementation("org.mongodb:bson-kotlinx:$mongo_version")

    // Websockets
    implementation("io.ktor:ktor-server-websockets-jvm")

    // CallLogging
    implementation("io.ktor:ktor-server-call-logging-jvm")

    // Sessions
    implementation("io.ktor:ktor-server-sessions-jvm")

    // Netty Engine
    implementation("io.ktor:ktor-server-netty-jvm")

    // Logback
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
