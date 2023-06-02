package dsdms.doctor.channels

import dsdms.doctor.model.domainServices.DomainResponseStatus
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import java.lang.IllegalStateException
import java.net.HttpURLConnection

/**
 * Allows communicate with DossierContext
 */
interface DossierServiceChannel {
    /**
     * @param dossierId
     * @return DomainResponseStatus
     * - OK
     * - DOSSIER_NOT_VALID
     * - DOSSIER_NOT_EXIST
     * @throws IllegalStateException if dossier context respond with not recognized status code
     */
    suspend fun checkDossierValidity(dossierId: String): DomainResponseStatus
}

class DossierServiceChannelImpl(val client: WebClient) : DossierServiceChannel {

    override suspend fun checkDossierValidity(dossierId: String): DomainResponseStatus {
        val statusCode = client.get("/dossiers/$dossierId").send()
            .await()
            .statusCode()
        return when (statusCode) {
            HttpURLConnection.HTTP_OK -> DomainResponseStatus.OK
            HttpURLConnection.HTTP_ACCEPTED -> DomainResponseStatus.DOSSIER_NOT_VALID
            HttpURLConnection.HTTP_NOT_FOUND -> DomainResponseStatus.DOSSIER_NOT_EXIST
            else -> throw IllegalStateException("Can not get dossier information")
        }
    }
}