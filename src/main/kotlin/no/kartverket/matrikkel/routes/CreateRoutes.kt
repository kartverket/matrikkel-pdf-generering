package no.kartverket.matrikkel.routes

import io.ktor.client.*
import io.ktor.server.routing.*
import no.kartverket.matrikkel.config.Configuration
import no.kartverket.matrikkel.create.createDocument


fun Route.createRoutes(config: Configuration, client: HttpClient) {
    post("/create-document") {
        createDocument(config.frontendUrl, client)
    }
}