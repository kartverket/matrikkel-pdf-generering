package no.kartverket.matrikkel

import io.ktor.server.application.*
import io.ktor.server.routing.*
import no.kartverket.matrikkel.routes.convertRoutes
import no.kartverket.matrikkel.routes.internalRoutes
import no.kartverket.matrikkel.routes.redirectRoutes

fun Application.configureRouting() {
    routing {
        internalRoutes()
        convertRoutes()
        redirectRoutes()
    }
}