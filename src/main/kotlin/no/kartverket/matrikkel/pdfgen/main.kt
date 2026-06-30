package no.kartverket.matrikkel.pdfgen

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.io.File
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.receive


@Serializable
data class PdfRequest(
    val html: String,
    val css: String
)

fun main() {
    embeddedServer(Netty, port = 8086) {
        // Aktiver JSON parsing
        install(ContentNegotiation) {
            json()
        }

        routing {

            post("/api/generate-pdf") {
                val generator = PdfGenerator()

                try {
                    val requestData = call.receive<PdfRequest>()

                    val pdfBytes = generator.convertDynamicHtmlAndCssToPdf(
                        htmlContent = requestData.html,
                        cssContent = requestData.css
                    )

                    call.respondBytes(
                        bytes = pdfBytes,
                        contentType = ContentType.Application.Pdf,
                        status = HttpStatusCode.OK
                    )

                } catch (e: Exception) {
                    call.respondText(
                        "Kunne ikke prosessere PDF: ${e.message}",
                        status = HttpStatusCode.InternalServerError
                    )
                    e.printStackTrace()
                }
            }
        }
    }.start(wait = true)
}
