package no.kartverket.matrikkel

import io.ktor.server.netty.*
import no.kartverket.matrikkel.config.Configuration

fun runApplication() {
    val config = Configuration()

    KtorServer.create(factory = Netty, port = 8086) {
        configureRouting(config)

    }.start(wait = true)
}