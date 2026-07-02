package no.kartverket.matrikkel.create

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import no.kartverket.matrikkel.api.FrontendClient
import no.kartverket.matrikkel.utils.htmlToDataUrl
import no.kartverket.matrikkel.utils.readPayload


suspend fun createDocument(frontendClient: FrontendClient, call: ApplicationCall) {
    val m22Payload = readPayload(call)
    val html = frontendClient.render(m22Payload)
    val documentUrl = htmlToDataUrl(html)

    call.respondText(
        text = """{"url":"$documentUrl"}""",
        contentType = ContentType.Application.Json,
        status = HttpStatusCode.OK,
    )
}