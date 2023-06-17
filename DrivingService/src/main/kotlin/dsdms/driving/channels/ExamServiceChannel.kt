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

package dsdms.driving.channels

import dsdms.driving.model.domainServices.DomainResponseStatus
import dsdms.driving.model.domainServices.DomainResponseStatus.PROVISIONAL_LICENSE_NOT_VALID
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import kotlinx.datetime.LocalDate
import java.lang.IllegalStateException

/**
 * Allows communicate with ExamContext.
 */
interface ExamServiceChannel {
    /**
     * Responds if provisional license of dossier is valid or not.
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

/**
 * @param client vertx web client.
 */
class ExamServiceChannelImpl(private val client: WebClient) : ExamServiceChannel {
    override suspend fun isProvisionalLicenseValid(dossierId: String, date: LocalDate): DomainResponseStatus {
        val result = client
            .get("/provisionalLicences/$dossierId/validity")
            .addQueryParam("date", date.toString())
            .send().await()
        return when (result.body().toString()) {
            DomainResponseStatus.OK.name -> DomainResponseStatus.OK
            PROVISIONAL_LICENSE_NOT_VALID.name -> PROVISIONAL_LICENSE_NOT_VALID
            "ID_NOT_FOUND" -> DomainResponseStatus.NO_PROVISIONAL_LICENSE
            else -> error("Provisional license state verification go wrong. Payload: ${result.body()}")
        }
    }
}
