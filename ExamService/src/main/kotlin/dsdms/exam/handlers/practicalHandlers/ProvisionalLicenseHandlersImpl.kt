package dsdms.exam.handlers.practicalHandlers

import dsdms.exam.handlers.domainConversionTable
import dsdms.exam.handlers.getHttpCode
import dsdms.exam.model.Model
import dsdms.exam.model.domainServices.DomainResponseStatus
import dsdms.exam.model.entities.ProvisionalLicense
import io.vertx.ext.web.RoutingContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.MissingFieldException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

class ProvisionalLicenseHandlersImpl(val model: Model) : ProvisionalLicenseHandlers {
    override fun registerProvisionalLicence(routingContext: RoutingContext) {
        val provisionalLicense: ProvisionalLicense = Json.decodeFromString(routingContext.body().asString())
        val response = model.provisionalLicenseService.registerProvisionalLicense(provisionalLicense)
        if(response == DomainResponseStatus.OK){
            routingContext.response().setStatusCode(HttpURLConnection.HTTP_OK).end(response.name)
        }else{
            routingContext.response().setStatusCode(domainConversionTable.getHttpCode(response)).end(response.name)
        }
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
