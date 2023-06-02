package dsdms.exam.handlers.provisionalLicense

import dsdms.exam.handlers.domainConversionTable
import dsdms.exam.handlers.getHttpCode
import dsdms.exam.model.Model
import dsdms.exam.model.domainServices.DomainResponseStatus
import dsdms.exam.model.entities.ProvisionalLicense
import io.vertx.ext.web.RoutingContext
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

class ProvisionalLicenseHandlersImpl(val model: Model) : ProvisionalLicenseHandlers {
    val cjson = Json{
        encodeDefaults = true
    }

    override suspend fun registerProvisionalLicence(routingContext: RoutingContext) {
        try {
            val provisionalLicense: ProvisionalLicense = cjson.decodeFromString(routingContext.body().asString())
            val response = model.provisionalLicenseService.registerProvisionalLicense(provisionalLicense)
            if (response == DomainResponseStatus.OK) {
                routingContext.response().setStatusCode(HttpURLConnection.HTTP_OK).end(response.name)
            } else {
                routingContext.response().setStatusCode(domainConversionTable.getHttpCode(response))
                    .end(response.name)
            }
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun getProvisionalLicenseHolder(routingContext: RoutingContext) {
        try {
            val dossierId = routingContext.request().getParam("id").toString()
            val provisionalLicenseHolder = model.provisionalLicenseService.getProvisionalLicenseHolder(dossierId)
            if (provisionalLicenseHolder == null) {
                routingContext.response()
                    .setStatusCode(domainConversionTable.getHttpCode(DomainResponseStatus.ID_NOT_FOUND))
                    .end(DomainResponseStatus.ID_NOT_FOUND.name)
            } else routingContext.response().setStatusCode(HttpURLConnection.HTTP_OK)
                .end(cjson.encodeToString(provisionalLicenseHolder))
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }

    override suspend fun isProvisionalLicenseValidHandler(routingContext: RoutingContext) {
        try{
            val dossierId = routingContext.request().getParam("id").toString()
            val date = LocalDate.parse(routingContext.queryParams().get("date"))
            println("Parsed date $date")
            val result = model.provisionalLicenseService.isProvisionalLicenseValid(dossierId, date)
            routingContext.response()
                .setStatusCode(domainConversionTable.getHttpCode(result))
                .end(result.name)
        } catch (ex: Exception) {
            handleException(ex, routingContext)
        }
    }
}
