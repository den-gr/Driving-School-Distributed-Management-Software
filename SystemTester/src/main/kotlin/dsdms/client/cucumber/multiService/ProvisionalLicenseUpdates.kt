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

package dsdms.client.cucumber.multiService

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.PracticalExamState
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * Provisional license updates test implementation.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/multiService/provisionalLicenseUpdate.feature"],
    plugin = ["pretty", "summary"],
)
class ProvisionalLicenseUpdates : En {
    private val dossierService: WebClient = VertxProviderImpl().getDossierServiceClient()
    private val examService: WebClient = VertxProviderImpl().getExamServiceClient()
    private val sleeper = SmartSleep()

    private var dossierId: String? = null

    init {
        Given("dossier {word} that is associated with a valid provisional license") { id: String ->
            dossierId = id
            val request = examService.get("/provisionalLicences/$dossierId").send()
            val response = sleeper.waitResult(request)
            assertEquals(HttpURLConnection.HTTP_OK, response?.statusCode())
        }
        When("register {word} state for practical exam") { state: String ->
            val request = examService
                .put("/provisionalLicences/$dossierId")
                .addQueryParam("practicalExamUpdate", state)
                .send()
            val response = sleeper.waitResult(request)
            checkResponse(response)
            assertEquals(HttpURLConnection.HTTP_OK, response?.statusCode())
        }
        Then("provisional license is deleted so {word}") { attendedResponse: String ->
            checkProvisionalLicenseDeletion(dossierId!!, attendedResponse)
        }
        And("dossier practical exam status is {word}") { finalState: String ->
            val dossier: Dossier = getDossier(dossierId!!)
            assertEquals(PracticalExamState.valueOf(finalState), dossier.examsStatus.practicalExamState)
        }

        Given("dossier {word} already has {word} and {int} failed attempts of practical exams") {
                id: String, examState: String, attempts: Int ->
            dossierId = id
            assertEquals(examState, getDossier(id).examsStatus.practicalExamState.name)
            assertEquals(attempts, getHolder(id).practicalExamAttempts)
        }
        When("register a practical exam fail, the number of failed attempts become {int}") { attempts: Int ->
            registerFailedPracticalExam(dossierId!!)
            assertEquals(attempts, getHolder(dossierId!!).practicalExamAttempts)
        }
        And("if do it again") {
            registerFailedPracticalExam(dossierId!!)
        }
        Then("provisional license is deleted so get {word} message") { status: String ->
            checkProvisionalLicenseDeletion(dossierId!!, status)
        }
        And("dossier is invalid and has practical exam state {word}") { status: String ->
            val response = getDossierResponse(dossierId!!)
            assertEquals(HttpURLConnection.HTTP_ACCEPTED, response?.statusCode())
            val dossier: Dossier = Json.decodeFromString(response?.body().toString())
            assertFalse(dossier.validity)
            assertEquals(status, dossier.examsStatus.practicalExamState.name)
        }
    }

    private fun getHolder(id: String): ProvisionalLicenseHolder {
        val request = examService.get("/provisionalLicences/$id").send()
        val response = sleeper.waitResult(request)
        checkResponse(response)
        assertEquals(HttpURLConnection.HTTP_OK, response?.statusCode())
        return Json.decodeFromString(response?.body().toString())
    }

    private fun getDossier(id: String): Dossier {
        val response = getDossierResponse(id)
        assertEquals(HttpURLConnection.HTTP_OK, response?.statusCode())
        return Json.decodeFromString(response?.body().toString())
    }

    private fun getDossierResponse(id: String): HttpResponse<Buffer>? {
        val request = dossierService.get("/dossiers/$id").send()
        val response = sleeper.waitResult(request)
        checkResponse(response)
        return response
    }

    private fun registerFailedPracticalExam(id: String) {
        val request = examService.put("/provisionalLicences/$id")
            .addQueryParam("practicalExamUpdate", "FAILED")
            .send()
        val response = sleeper.waitResult(request)
        checkResponse(response)
        assertEquals(HttpURLConnection.HTTP_OK, response?.statusCode())
    }

    private fun checkProvisionalLicenseDeletion(id: String, attendedResponse: String) {
        val request = examService.get("/provisionalLicences/$id").send()
        val response = sleeper.waitResult(request)
        checkResponse(response)
        assertEquals(attendedResponse, response?.body().toString())
        assertEquals(HttpURLConnection.HTTP_NOT_FOUND, response?.statusCode())
    }
}
