package no.kartverket.matrikkel.create

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.respondBytes
import kotlinx.serialization.Serializable
import no.kartverket.matrikkel.api.FrontendClient
import no.kartverket.matrikkel.pdfgen.PdfService
import no.kartverket.matrikkel.utils.readPayload

@Serializable
data class DocumentResponse(val url: String)

suspend fun createDocument(
    frontendClient: FrontendClient,
    pdfService: PdfService,
    call: ApplicationCall,
) {
    val m22Payload = readPayload(call)
    val html = frontendClient.render(m22Payload)
    val pdfBytes = pdfService.htmlToPdf(html)

    call.respondBytes(pdfBytes, ContentType.Application.Pdf, HttpStatusCode.OK)
}