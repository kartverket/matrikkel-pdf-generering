package no.kartverket.matrikkel.create

import io.ktor.client.*
import io.ktor.client.request.*


suspend fun createDocument(frontendUrl: String, client: HttpClient, m22Payload: String) {


    val frontendData = client.post("$frontendUrl/create-document") {
        setBody(m22Payload)
    }

    // Starte generering av pdf her
}