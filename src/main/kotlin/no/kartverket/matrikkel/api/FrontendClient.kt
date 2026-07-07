package no.kartverket.matrikkel.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.JsonElement
import no.kartverket.matrikkel.ServiceException
import no.kartverket.matrikkel.UpstreamException
import java.io.IOException


interface FrontendClient {
    suspend fun render(m22Payload: JsonElement): String
}

class HttpFrontendClient(
    private val client: HttpClient,
    private val baseUrl: String,
) : FrontendClient {

    constructor(baseUrl: String) : this(defaultHttpClient(), baseUrl)

    override suspend fun render(m22Payload: JsonElement): String {
        val response = executeRequest {
            client.post("$baseUrl/render") {
                contentType(ContentType.Application.Json)
                setBody(m22Payload)
            }
        }

        if (!response.status.isSuccess()) {
            throw UpstreamException(
                status = response.status,
                body = response.bodyAsText(),
                contentType = response.contentType(),
            )
        }

        return response.bodyAsText()
    }

    private suspend fun executeRequest(request: suspend () -> HttpResponse): HttpResponse = try {
        request()
    } catch (e: HttpRequestTimeoutException) {
        throw ServiceException.gatewayTimeout(message = "Timeout while contacting frontend", cause = e)
    } catch (e: IOException) {
        throw ServiceException.badGateway(message = "Failed to contact frontend", cause = e)
    }

    companion object {
        private fun defaultHttpClient(): HttpClient = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 10_000 // 10 sekunder
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }
}
