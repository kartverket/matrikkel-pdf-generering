plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(ktorLibs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    application
}

group = "no.kartverket.matrikkel"
version = "1.0.0-SNAPSHOT"

application {
    mainClass = "no.kartverket.matrikkel.MainKt"
}

kotlin {
    jvmToolchain(25)
}
dependencies {
    implementation(ktorLibs.serialization.kotlinx.json)
    implementation(ktorLibs.server.auth)
    implementation(ktorLibs.server.auth.jwt)
    implementation(ktorLibs.server.callId)
    implementation(ktorLibs.server.callLogging)
    implementation(ktorLibs.server.contentNegotiation)
    implementation(ktorLibs.server.core)
    implementation(ktorLibs.server.cors)
    implementation(ktorLibs.server.forwardedHeader)
    implementation(ktorLibs.server.metrics.micrometer)
    implementation(ktorLibs.server.netty)
    implementation(ktorLibs.server.openapi)
    implementation(ktorLibs.server.routingOpenapi)
    implementation(ktorLibs.server.statusPages)
    implementation(ktorLibs.server.swagger)
    implementation(libs.logback.classic)
    implementation(libs.micrometer.registryPrometheus)
    implementation(libs.flyway)
    implementation(libs.hikari)


    testImplementation(kotlin("test"))
    testImplementation(ktorLibs.server.testHost)
}
