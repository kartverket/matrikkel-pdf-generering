package no.kartverket.matrikkel

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import no.kartverket.matrikkel.api.HttpFrontendClient
import no.kartverket.matrikkel.config.Configuration
import no.kartverket.matrikkel.pdfgen.GotenbergPdfService
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.slf4j.LoggerFactory
import java.util.*


val log = LoggerFactory.getLogger("matrikkel-pdf-generering")

fun runApplication() {
    val config = Configuration()
    val frontendClient = HttpFrontendClient(config.frontendUrl)
    val pdfService = GotenbergPdfService(config.gotenbergUrl)

    KtorServer.create(factory = Netty, port = 8086) {
        configureRouting(frontendClient, pdfService)
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
    install(ContentNegotiation) {
        json()
    }
    install(StatusPages) {
        configureExceptionHandling()
    }
}