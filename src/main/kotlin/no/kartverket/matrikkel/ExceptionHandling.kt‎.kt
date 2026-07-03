package no.kartverket.matrikkel

import io.ktor.http.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.Serializable

fun StatusPagesConfig.configureExceptionHandling() {

    exception<UpstreamException> { call, cause ->
        log.warn(
            "Upstream returned {} for {} {}",
            cause.status.value,
            call.request.httpMethod.value,
            call.request.path(),
        )
        call.respondText(
            text = cause.body,
            contentType = cause.contentType ?: ContentType.Text.Plain,
            status = cause.status,
        )
    }

    exception<ServiceException> { call, cause ->
        if (cause.cause != null) {
            log.warn(
                "{} {} -> {} {}: {}",
                call.request.httpMethod.value,
                call.request.path(),
                cause.status.value,
                cause.code,
                cause.message,
                cause.cause,
            )
        } else {
            log.warn(
                "{} {} -> {} {}: {}",
                call.request.httpMethod.value,
                call.request.path(),
                cause.status.value,
                cause.code,
                cause.message,
            )
        }
        call.respond(cause.status, ErrorResponse(cause.code, cause.message))
    }

    exception<BadRequestException> { call, cause ->
        log.warn("Bad request on {} {}: {}", call.request.httpMethod.value, call.request.path(), cause.message)
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse("invalidRequest", cause.message ?: "The request is invalid"),
        )
    }

    exception<IllegalArgumentException> { call, cause ->
        log.warn("Illegal argument on {} {}: {}", call.request.httpMethod.value, call.request.path(), cause.message)
        call.respond(
            HttpStatusCode.BadRequest,
            ErrorResponse("invalidRequest", cause.message ?: "The request is invalid"),
        )
    }

    exception<Throwable> { call, cause ->
        if (cause is CancellationException) throw cause
        log.error("Unhandled exception on {} {}", call.request.httpMethod.value, call.request.path(), cause)
        call.respond(
            HttpStatusCode.InternalServerError,
            ErrorResponse("internal_error", "An internal error occurred"),
        )
    }

    status(HttpStatusCode.NotFound) { call, status ->
        call.respond(status, ErrorResponse("not_found", "Resource not found"))
    }
    status(HttpStatusCode.MethodNotAllowed) { call, status ->
        call.respond(status, ErrorResponse("method_not_allowed", "Method not allowed"))
    }
}

@Serializable
data class ErrorResponse(
    val code: String,
    val message: String,
)

class UpstreamException(
    val status: HttpStatusCode,
    val body: String,
    val contentType: ContentType?,
) : RuntimeException("Upstream returned ${status.value}")

class ServiceException(
    val status: HttpStatusCode,
    val code: String,
    override val message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    companion object {
        fun badRequest(
            code: String = "bad_request",
            message: String
        ) = ServiceException(
            status = HttpStatusCode.BadRequest,
            code = code,
            message = message
        )

        fun badGateway(
            code: String = "bad_gateway",
            message: String,
            cause: Throwable? = null,
        ) = ServiceException(
            status = HttpStatusCode.BadGateway,
            code = code,
            message = message,
            cause = cause,
        )

        fun gatewayTimeout(
            code: String = "gateway_timeout",
            message: String,
            cause: Throwable? = null,
        ) = ServiceException(
            status = HttpStatusCode.GatewayTimeout,
            code = code,
            message = message,
            cause = cause,
        )
    }
}