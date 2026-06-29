package no.kartverket.matrikkel.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route


fun Route.internalRoutes() {
    route("internal") {
        get("isAlive") {
            call.respondText("Alive")
        }

        get("isReady") {
            call.respondText("Ready")
        }

        get("selftest") {
            call.respondText("Helloworld")
        }
    }}