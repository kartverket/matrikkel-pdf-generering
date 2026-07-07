package no.kartverket.matrikkel.utils

import java.nio.charset.StandardCharsets
import java.util.Base64

fun htmlToDataUrl(html: String): String {
    val encoded = Base64.getEncoder().encodeToString(html.toByteArray(StandardCharsets.UTF_8))
    return "data:text/html;charset=utf-8;base64,$encoded"
}

