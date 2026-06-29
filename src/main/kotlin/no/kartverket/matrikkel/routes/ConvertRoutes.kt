package no.kartverket.matrikkel.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route


fun Route.convertRoutes() {
    route("convert") {
        post("pdf") {
            call.respondText("PDF")
        }
    }
}