package no.kartverket.matrikkel.pdfgen

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class PdfGenerator {
    fun convertHtmlToPdf(htmlContent: String, resourceFolder: File): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val converterProperties = ConverterProperties()
        converterProperties.setBaseUri(resourceFolder.toURI().toString())

        val inputStream = ByteArrayInputStream(htmlContent.toByteArray(Charsets.UTF_8))

        HtmlConverter.convertToPdf(inputStream, outputStream, converterProperties)

        return outputStream.toByteArray()
    }
}
