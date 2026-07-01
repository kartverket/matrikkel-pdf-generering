package no.kartverket.matrikkel

import io.ktor.client.*
import io.ktor.server.netty.*
import no.kartverket.matrikkel.config.Configuration

fun runApplication() {
    val config = Configuration()
    val client = HttpClient()

    KtorServer.create(factory = Netty, port = 8086) {
        configureRouting(config, client)

    }.start(wait = true)
}