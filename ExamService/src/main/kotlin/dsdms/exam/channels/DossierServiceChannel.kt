package dsdms.exam.channels

import dsdms.exam.model.domainServices.DomainResponseStatus
import dsdms.exam.model.valueObjects.ExamEvent
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import java.lang.IllegalStateException
import java.net.HttpURLConnection

/**
 * Allows to communicate with DossierContext.
 */
interface DossierServiceChannel {
    /**
     * Update dossier exam status
     * @param dossierId
     * @param examEvent event about exam state change
     * @return DomainResponseStatus
     *  - OK
     *  - EXAM_STATUS_UPDATE_ERROR
     */
    suspend fun updateExamStatus(dossierId: String, examEvent: ExamEvent): DomainResponseStatus

    /**
     * Verify if dossier is valid or not.
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

    override suspend fun updateExamStatus(dossierId: String, examEvent: ExamEvent): DomainResponseStatus {
        val result = client.put("/dossiers/$dossierId/examStatus")
            .sendBuffer(Buffer.buffer(examEvent.name)).await()
        if (result.statusCode() != HttpURLConnection.HTTP_OK) {
            println(result.body())
            return DomainResponseStatus.EXAM_STATUS_UPDATE_ERROR
        }
        return DomainResponseStatus.OK
    }

    override suspend fun checkDossierValidity(dossierId: String): DomainResponseStatus {
        val statusCode = client.get("/dossiers/$dossierId").send()
            .await()
            .statusCode()
        return when (statusCode) {
            HttpURLConnection.HTTP_OK -> DomainResponseStatus.OK
            HttpURLConnection.HTTP_ACCEPTED -> DomainResponseStatus.DOSSIER_NOT_VALID
            HttpURLConnection.HTTP_NOT_FOUND -> DomainResponseStatus.DOSSIER_NOT_EXIST
            else -> error("Can not get dossier information")
        }
    }
}
