package dsdms.doctor.model.domainServices.vertxClient

import io.vertx.ext.web.client.WebClient

interface VertxClient {
    fun getDossierServiceClient(): WebClient
}

