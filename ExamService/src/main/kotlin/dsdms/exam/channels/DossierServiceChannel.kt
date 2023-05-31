package dsdms.exam.channels

import dsdms.exam.model.domainServices.DomainResponseStatus
import dsdms.exam.model.valueObjects.ExamResultEvent
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

interface DossierServiceChannel {
    suspend fun updateExamStatus(dossierId: String, examResultEvent: ExamResultEvent): DomainResponseStatus
}

class DossierServiceChannelImpl(val client: WebClient) : DossierServiceChannel {

    override suspend fun updateExamStatus(dossierId: String, examResultEvent: ExamResultEvent): DomainResponseStatus {
        val result = client.put("/dossiers/$dossierId/examStatus")
            .sendBuffer(Buffer.buffer(Json.encodeToString(examResultEvent))).await()
        if(result.statusCode() != HttpURLConnection.HTTP_OK){
            println(result.body())
            return DomainResponseStatus.EXAM_STATUS_ERROR
        }
        return DomainResponseStatus.OK
    }
}
