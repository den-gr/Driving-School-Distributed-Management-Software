package dsdms.exam.handlers

import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import java.net.HttpURLConnection

interface Handlers {

    /**
     * To handle exceptions in route handlers
     * @param ex -> exception type to be caught
     * @param routingContext -> where to send message if some exceptions occurs
     */
    @OptIn(ExperimentalSerializationApi::class)
    fun handleException(ex: Exception, routingContext: RoutingContext){
        println("Error message: ${ex.message}")
        when(ex){
            is SerializationException, is MissingFieldException -> {
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST).end(ex.message)
            }
            else ->{
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR).end(ex.message)
            }
        }
    }
}