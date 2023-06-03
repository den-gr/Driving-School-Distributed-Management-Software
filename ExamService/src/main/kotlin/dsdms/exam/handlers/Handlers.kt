package dsdms.exam.handlers

import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import java.lang.IllegalArgumentException
import java.net.HttpURLConnection

interface Handlers {
    /**
     * Extract id parameter from uri.
     * @param routingContext
     * @return dossierId
     */
    fun getDossierId(routingContext: RoutingContext): String {
        return routingContext.request().getParam("id").toString()
    }

    /**
     * To handle exceptions in route handlers.
     * @param ex -> exception type to be caught
     * @param routingContext -> where to send message if some exceptions occurs
     */
    @OptIn(ExperimentalSerializationApi::class)
    fun handleException(ex: Exception, routingContext: RoutingContext) {
        println("Error message: ${ex.message}")
        when (ex) {
            is SerializationException, is MissingFieldException, is IllegalArgumentException -> {
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
            }
            else -> {
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end(ex.message)
            }
        }
    }
}
