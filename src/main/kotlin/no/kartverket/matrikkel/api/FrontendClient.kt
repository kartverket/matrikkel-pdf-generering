package no.kartverket.matrikkel.api

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import no.kartverket.matrikkel.ServiceException
import no.kartverket.matrikkel.UpstreamException
import java.io.IOException


interface FrontendClient {
    suspend fun render(m22Payload: String): String
}

class HttpFrontendClient(
    private val client: HttpClient,
    private val baseUrl: String,
) : FrontendClient {

    override suspend fun render(m22Payload: String): String {
        val response: HttpResponse = try {
            client.post("$baseUrl/render") {
                contentType(ContentType.Application.Json)
                setBody(m22Payload)
            }
        } catch (e: HttpRequestTimeoutException) {
            throw ServiceException.gatewayTimeout(message = "Timeout while contacting frontend", cause = e)
        } catch (e: IOException) {
            throw ServiceException.badGateway(message = "Failed to contact frontend", cause = e)
        }

        val body = response.bodyAsText()
        if (!response.status.isSuccess()) {
            throw UpstreamException(
                status = response.status,
                body = body,
                contentType = response.contentType(),
            )
        }
        return body
    }
}
