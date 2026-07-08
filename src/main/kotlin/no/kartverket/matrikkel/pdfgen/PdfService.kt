package no.kartverket.matrikkel.pdfgen

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class PdfService(
    private val converterProperties: ConverterProperties = ConverterProperties(),
) {
    fun htmlToPdf(html: String): ByteArray {
        ByteArrayInputStream(html.toByteArray(Charsets.UTF_8)).use { input ->
            ByteArrayOutputStream().use { output ->
                HtmlConverter.convertToPdf(input, output, converterProperties)
                return output.toByteArray()
            }
        }
    }
}
