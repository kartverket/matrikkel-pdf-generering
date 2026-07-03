package no.kartverket.matrikkel

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.server.netty.*
import no.kartverket.matrikkel.api.HttpFrontendClient
import no.kartverket.matrikkel.config.Configuration


fun runApplication() {
    val config = Configuration()
    val client = HttpClient(CIO) {

        install(HttpTimeout) {
            requestTimeoutMillis = 10000 // 10 sekunder
        }
    }

    val frontendClient = HttpFrontendClient(client, config.frontendUrl)

    KtorServer.create(factory = Netty, port = 8086) {
        configureRouting(frontendClient)

    }.start(wait = true)
}