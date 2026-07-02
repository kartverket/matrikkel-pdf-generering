package no.kartverket.matrikkel.routes

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val httpClient = HttpClient()

fun Route.redirectRoutes() {
        post("/create-document") {

            val payload = call.receiveText()

            val frontendUrl = System.getenv("FRONTEND_URL") ?: "http://localhost:3000"

            val response: HttpResponse = httpClient.post("$frontendUrl/render") {
                contentType(ContentType.Application.Json)
                setBody(payload)
            }
            val html = response.bodyAsText()
            call.respondText(html, ContentType.Text.Html)
        }
    }