package dsdms.exam.handlers.practicalHandlers

import dsdms.exam.model.Model
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import java.net.HttpURLConnection

class ProvisionalLicenseHandlersImpl(val model: Model) : ProvisionalLicenseHandlers {
    override fun registerProvisionalLicence(routingContext: RoutingContext) {
        routingContext.response().setStatusCode(HttpURLConnection.HTTP_OK).end(" ")
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun handleException(ex: Exception, routingContext: RoutingContext){
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
