package no.kartverket.matrikkel.create

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.request.*


suspend fun createDocument(frontendUrl: String, client: HttpClient, call: io.ktor.server.application.ApplicationCall) {
    val m22Payload = call.receiveText()


    val response: HttpResponse = client.post("$frontendUrl/create-document") {
        contentType(ContentType.Application.Json)
        setBody(m22Payload)
    }


    val frontendData = response.bodyAsText()

    // Starte generering av pdf her
    println(frontendData)

}