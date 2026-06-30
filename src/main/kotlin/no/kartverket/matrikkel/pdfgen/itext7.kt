package no.kartverket.matrikkel.pdfgen

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class PdfGenerator {
    val outputStream = ByteArrayOutputStream()
    val converterProperties = ConverterProperties()

    fun convertDynamicHtmlAndCssToPdf(htmlContent: String, cssContent: String): ByteArray {
        // Legger til CSS direkte i HTML før konverteringen, så iText ikke leter etter eksterne filer på disk

        val nestedHtml = if (htmlContent.contains("<head>")) {
            htmlContent.replace("<head>", "<head><style>$cssContent</style>")
        } else {
            // Hvis HTML mangler <head> lager vi en style-blokk på toppen
            "<style>$cssContent</style>$htmlContent"
        }

        val inputStream = ByteArrayInputStream(nestedHtml.toByteArray(Charsets.UTF_8))

        // Kjører konverteringen direkte i minnet
        HtmlConverter.convertToPdf(inputStream, outputStream, converterProperties)

        return outputStream.toByteArray()
    }
}
