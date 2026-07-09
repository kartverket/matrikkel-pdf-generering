package no.kartverket.matrikkel.pdfgen

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import no.kartverket.matrikkel.ServiceException
import java.io.IOException

class GotenbergPdfService(
    private val client: HttpClient,
    private val baseUrl: String,
) : PdfService {

    constructor(baseUrl: String) : this(defaultHttpClient(), baseUrl)

    override suspend fun htmlToPdf(html: String): ByteArray {
        val response = try {
            client.post("$baseUrl/forms/chromium/convert/html") {
                setBody(
                    MultiPartFormDataContent(
                        formData {
                            append(
                                "files",
                                html.toByteArray(Charsets.UTF_8),
                                Headers.build {
                                    append(HttpHeaders.ContentType, ContentType.Text.Html.toString())
                                    append(HttpHeaders.ContentDisposition, "filename=\"index.html\"")
                                },
                            )
                            append("printBackground", "true")
                            append("preferCssPageSize", "true")
                        },
                    ),
                )
            }
        } catch (e: HttpRequestTimeoutException) {
            throw ServiceException.gatewayTimeout(message = "Timeout while contacting Gotenberg", cause = e)
        } catch (e: IOException) {
            throw ServiceException.badGateway(message = "Failed to contact Gotenberg", cause = e)
        }

        if (!response.status.isSuccess()) {
            throw ServiceException.badGateway(
                message = "Gotenberg returned ${response.status.value}: ${response.bodyAsText()}",
            )
        }

        return response.readRawBytes()
    }

    companion object {
        private fun defaultHttpClient(): HttpClient = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000 // 30 sekunder
            }
        }
    }
}
