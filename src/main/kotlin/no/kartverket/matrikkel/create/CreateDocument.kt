package no.kartverket.matrikkel.create

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.server.request.*


suspend fun createDocument(frontendUrl: String, client: HttpClient, call: io.ktor.server.application.ApplicationCall) {
    val m22Payload = call.receiveText()


    val frontendData = client.post("$frontendUrl/create-document") {
        setBody(m22Payload)
    }

    // Starte generering av pdf her
}