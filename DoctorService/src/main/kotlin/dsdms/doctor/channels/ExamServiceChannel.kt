package dsdms.doctor.channels

import dsdms.doctor.model.domainServices.DomainResponseStatus
import dsdms.doctor.model.valueObjects.DoctorApprovalEvent
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.lang.IllegalStateException
import java.net.HttpURLConnection

/**
 * Allows communicate with ExamContext
 */
interface ExamServiceChannel {
    /**
     * Notify ExamContext that subscriber get doctor approval so he/she can have a theoretical exam pass
     * @param doctorApprovalEvent
     * @return DomainResponseStatus
     *  - OK
     *  - EXAM_PASS_ALREADY_AVAILABLE
     * @throws IllegalStateException in case of unrecognized response of ExamContext
     */
    suspend fun notifyAboutDoctorApproval(doctorApprovalEvent: DoctorApprovalEvent): DomainResponseStatus
}

class ExamServiceChannelImpl(private val client: WebClient): ExamServiceChannel{
    override suspend fun notifyAboutDoctorApproval(doctorApprovalEvent: DoctorApprovalEvent): DomainResponseStatus {
        val statusCode = client.put("/theoreticalExam/pass")
            .sendBuffer(Buffer.buffer(Json.encodeToString(doctorApprovalEvent)))
            .await()
            .statusCode()
        return when(statusCode){
            HttpURLConnection.HTTP_OK -> DomainResponseStatus.OK
            HttpURLConnection.HTTP_BAD_REQUEST -> DomainResponseStatus.EXAM_PASS_ALREADY_AVAILABLE
            else -> {
                println(statusCode)
                throw IllegalStateException("Can not notify about doctor approval event. Arrived status code: $statusCode")
            }
        }
    }
}