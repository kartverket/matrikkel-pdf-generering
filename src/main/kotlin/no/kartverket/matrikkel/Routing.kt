package no.kartverket.matrikkel
import io.ktor.server.application.*
import io.ktor.server.routing.*
import no.kartverket.matrikkel.api.FrontendClient
import no.kartverket.matrikkel.routes.createRoutes
import no.kartverket.matrikkel.routes.internalRoutes

fun Application.configureRouting(frontendClient: FrontendClient) {
    routing {
        internalRoutes()
        createRoutes(frontendClient)
    }
}