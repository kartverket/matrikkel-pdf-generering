package no.kartverket.matrikkel

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import no.kartverket.matrikkel.api.HttpFrontendClient
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import no.kartverket.matrikkel.config.Configuration
import org.slf4j.LoggerFactory
import java.util.*


val log = LoggerFactory.getLogger("matrikkel-pdf-generering")

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
        standardPlugins()

    }.start(wait = true)
}

fun Application.standardPlugins() {
    install(CallId) {
        header(HttpHeaders.XRequestId)
        generate { UUID.randomUUID().toString() }
        verify { it.isNotBlank() }
    }
    install(CallLogging) {
        logger = no.kartverket.matrikkel.log
        disableDefaultColors()
        filter { call -> call.request.path().contains("/internal/").not() }
        mdc("RequestId") { it.callId }
    }
    install(StatusPages) {
        configureExceptionHandling()
    }
}