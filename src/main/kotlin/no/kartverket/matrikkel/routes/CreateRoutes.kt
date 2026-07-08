package no.kartverket.matrikkel.routes

import io.ktor.client.*
import io.ktor.server.routing.*
import no.kartverket.matrikkel.api.FrontendClient
import no.kartverket.matrikkel.config.Configuration
import no.kartverket.matrikkel.create.createDocument
import no.kartverket.matrikkel.pdfgen.PdfService


fun Route.createRoutes( frontendClient: FrontendClient, pdfService: PdfService) {
    post("/create-document") {
        createDocument(frontendClient, pdfService, call)
    }
}