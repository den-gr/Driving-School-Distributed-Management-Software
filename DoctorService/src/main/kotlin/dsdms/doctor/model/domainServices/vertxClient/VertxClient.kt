package dsdms.doctor.model.domainServices.vertxClient

import io.vertx.ext.web.client.WebClient

interface VertxClient {
    suspend fun getDossierServiceClient(): WebClient
    suspend fun getExamServiceClient(): WebClient
}

