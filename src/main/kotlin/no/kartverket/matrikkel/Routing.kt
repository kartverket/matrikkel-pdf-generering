package no.kartverket.matrikkel

import io.ktor.server.application.*
import io.ktor.server.routing.*
import no.kartverket.matrikkel.config.Configuration
import no.kartverket.matrikkel.routes.convertRoutes
import no.kartverket.matrikkel.routes.createRoutes
import no.kartverket.matrikkel.routes.internalRoutes

fun Application.configureRouting(config: Configuration) {
    routing {
        internalRoutes()
        convertRoutes()
        createRoutes(config)
    }
}