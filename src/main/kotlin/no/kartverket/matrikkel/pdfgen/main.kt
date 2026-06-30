package no.kartverket.matrikkel.pdfgen

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {

            get("/generate-pdf") {
                val generator = PdfGenerator()
                val distFolder = File("dist")
                val htmlFile = File(distFolder,"index.html")

                if (!htmlFile.exists()) {
                    call.respond(HttpStatusCode.NotFound, "HTML file not found")
                    return@get
                }

                try {
                    val htmlContent = htmlFile.readText(Charsets.UTF_8)
                    val pdfBytes = generator.convertHtmlToPdf(htmlContent, distFolder)

                    // Send PDF tilbake til nettleseren
                    call.response.header(
                        HttpHeaders.ContentDisposition, ContentDisposition.Attachment.withParameter(
                        ContentDisposition.Parameters.FileName, "react_rapport.pdf").toString()
                    )
                    call.respondBytes(pdfBytes, ContentType.Application.Pdf, HttpStatusCode.OK)

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "Error generating PDF: ${e.message}")
                }
            }
        }
    }.start(wait = true)
}
