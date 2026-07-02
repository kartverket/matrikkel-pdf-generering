package no.kartverket.matrikkel.utils

import io.ktor.server.application.*
import io.ktor.server.request.*
import kotlinx.coroutines.CancellationException
import no.kartverket.matrikkel.ServiceException

suspend fun readPayload(call: ApplicationCall): String {
    val payload = try {
        call.receiveText()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        throw ServiceException.badRequest(message = "Invalid or unreadable request body")
    }

    if (payload.isBlank()) {
        throw ServiceException.badRequest(message = "Request body must not be empty")
    }

    return payload
}
