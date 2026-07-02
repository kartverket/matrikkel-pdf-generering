package no.kartverket.matrikkel

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import no.kartverket.matrikkel.routes.internalRoutes
import kotlin.test.Test
import kotlin.test.assertEquals

class ServerTest {

    @Test
    fun `test isAlive endpoint`() = testApplication {
        application {
            routing {
                internalRoutes()
            }
        }
        // verify isAlive returns 200
        assertEquals(HttpStatusCode.OK, client.get("/internal/isAlive").status)
    }

}