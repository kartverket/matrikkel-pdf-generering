package no.kartverket.matrikkel.pdfgen

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class PdfService(
    private val converterProperties: ConverterProperties = ConverterProperties(),
) {
    fun htmlToPdf(html: String, css: String): ByteArray {
        val document = if (html.contains("<head>")) {
            html.replace("<head>", "<head><style>$css</style>")
        } else {
            "<style>$css</style>$html"
        }

        ByteArrayInputStream(document.toByteArray(Charsets.UTF_8)).use { input ->
            ByteArrayOutputStream().use { output ->
                HtmlConverter.convertToPdf(input, output, converterProperties)
                return output.toByteArray()
            }
        }
    }
}
