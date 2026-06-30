package no.kartverket.matrikkel.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post


fun Route.redirectRoutes() {
        post("/create-document") {
            call.respondText("Sending data to Bun")
        }
    }