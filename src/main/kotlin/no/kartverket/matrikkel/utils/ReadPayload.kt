package no.kartverket.matrikkel.utils

import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.JsonElement
import no.kartverket.matrikkel.ServiceException

suspend fun readPayload(call: ApplicationCall): JsonElement {
    return try {
        call.receive<JsonElement>()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        throw ServiceException.badRequest(message = "Invalid or unreadable JSON request body")
    }
}
