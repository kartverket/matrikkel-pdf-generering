package no.kartverket.matrikkel.routes

import io.ktor.server.routing.*
import no.kartverket.matrikkel.api.FrontendClient
import no.kartverket.matrikkel.create.createDocument


fun Route.createRoutes(frontendClient: FrontendClient) {
    post("/create-document") {
        createDocument(frontendClient, call)
    }
}