/*******************************************************************************
 * MIT License
 *
 * Copyright 2023 DSDMS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 ******************************************************************************/

package dsdms.doctor.channels

import dsdms.doctor.model.domainServices.DomainResponseStatus
import dsdms.doctor.model.valueObjects.DoctorApprovalEvent
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.lang.IllegalStateException
import java.net.HttpURLConnection

/**
 * Allows to communicate with ExamContext.
 */
interface ExamServiceChannel {
    /**
     * Notify ExamContext that subscriber get doctor approval so he/she can have a theoretical exam pass.
     * @param doctorApprovalEvent
     * @return DomainResponseStatus
     *  - OK
     *  - EXAM_PASS_ALREADY_AVAILABLE
     * @throws IllegalStateException in case of unrecognized response of ExamContext
     */
    suspend fun notifyAboutDoctorApproval(doctorApprovalEvent: DoctorApprovalEvent): DomainResponseStatus
}

/**
 * Vertx channel implementation.
 * @param client of ExamService
 */
class ExamServiceChannelImpl(private val client: WebClient) : ExamServiceChannel {
    override suspend fun notifyAboutDoctorApproval(doctorApprovalEvent: DoctorApprovalEvent): DomainResponseStatus {
        val statusCode = client.put("/theoreticalExam/pass")
            .sendBuffer(Buffer.buffer(Json.encodeToString(doctorApprovalEvent)))
            .await()
            .statusCode()
        return when (statusCode) {
            HttpURLConnection.HTTP_OK -> DomainResponseStatus.OK
            HttpURLConnection.HTTP_BAD_REQUEST -> DomainResponseStatus.EXAM_PASS_ALREADY_AVAILABLE
            else -> {
                println(statusCode)
                error("Can not notify about doctor approval event. Arrived status code: $statusCode")
            }
        }
    }
}
