package no.kartverket.matrikkel.pdfgen

interface PdfService {
    suspend fun htmlToPdf(html: String): ByteArray
}
