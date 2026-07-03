package no.kartverket.matrikkel.utils

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun htmlToDataUrl(html: String): String {
    val encoded = URLEncoder.encode(html, StandardCharsets.UTF_8).replace("+", "%20")
    return "data:text/html;charset=utf-8,$encoded"
}