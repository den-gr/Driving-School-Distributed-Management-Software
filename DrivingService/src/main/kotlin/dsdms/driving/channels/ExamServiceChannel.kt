package dsdms.driving.channels

import dsdms.driving.model.domainServices.DomainResponseStatus
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import kotlinx.datetime.LocalDate
import java.lang.IllegalStateException

/**
 * Allows communicate with ExamContext
 */
interface ExamServiceChannel {
    /**
     * Responds if provisional license of dossier is valid or not
     * @param dossierId of provisional license owner
     * @param date when provisional license should be valid
     * @return DomainResponseStatus
     *  - OK
     *  - PROVISIONAL_LICENSE_NOT_VALID
     *  - NO_PROVISIONAL_LICENSE
     * @throws IllegalStateException in case when exam context responds with an unrecognised message
     */
    suspend fun isProvisionalLicenseValid(dossierId: String, date: LocalDate): DomainResponseStatus
}

class ExamServiceChannelImpl(private val client: WebClient) : ExamServiceChannel {
    override suspend fun isProvisionalLicenseValid(dossierId: String, date: LocalDate): DomainResponseStatus {
        val result = client
            .get("/provisionalLicences/$dossierId/validity")
            .addQueryParam("date", date.toString())
            .send().await()
        return when (result.body().toString()) {
            DomainResponseStatus.OK.name -> DomainResponseStatus.OK
            DomainResponseStatus.PROVISIONAL_LICENSE_NOT_VALID.name -> DomainResponseStatus.PROVISIONAL_LICENSE_NOT_VALID
            "ID_NOT_FOUND" -> DomainResponseStatus.NO_PROVISIONAL_LICENSE
            else -> throw IllegalStateException("Provisional license state verification go wrong. Payload: ${result.body()}")
        }
    }
}
