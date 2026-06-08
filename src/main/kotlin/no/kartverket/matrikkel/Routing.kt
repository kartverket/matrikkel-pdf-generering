package no.kartverket.matrikkel

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }

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
        }
    }
}