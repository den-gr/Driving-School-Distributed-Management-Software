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
import dsdms.client.utils.createJson
import dsdms.doctor.model.valueObjects.DoctorResult
import dsdms.doctor.model.valueObjects.ResultTypes
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals

/**
 * Exam event test implementation.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/multiService/theoreticalExamPassTest.feature"],
    plugin = ["pretty", "summary"],
)
class ExamEventPassTest : En {
    private val doctorService: WebClient = VertxProviderImpl().getDoctorServiceClient()
    private val examService: WebClient = VertxProviderImpl().getExamServiceClient()
    private var value: String = ""
    private var retrievedTheoreticalExamPass: TheoreticalExamPass? = null
    private var statusCode: Int? = null

    init {
        val sleeper = SmartSleep()

        When("secretary register {word}, {word}, {word}") { id: String, date: String, result: String ->
            val request = doctorService
                .put("/doctorSlots")
                .sendBuffer(createJson(DoctorResult(id, date, ResultTypes.valueOf(result))))
            val response = sleeper.waitResult(request)

            checkResponse(response)
            statusCode = response?.statusCode()
            value = response?.body().toString()
        }
        Then("request finished with success, with code {int}") { code: Int ->
            assertEquals(HTTP_OK, code)
        }
        And("theoretical exam pass is registered for dossier {word}") { dossierId: String ->
            val request = examService
                .get("/theoreticalExam/$dossierId/pass")
                .send()
            val response = sleeper.waitResult(request)

            checkResponse(response)
            assertEquals(HTTP_OK, response?.statusCode())

            retrievedTheoreticalExamPass = Json.decodeFromString(response?.body().toString())
        }
        When("sending {word}, {word} and {word} for saving doctor results") {
                id: String, date: String, result: String ->
            val request = doctorService
                .put("/doctorSlots")
                .sendBuffer(createJson(DoctorResult(id, date, ResultTypes.valueOf(result))))
            val response = sleeper.waitResult(request)

            checkResponse(response)
            statusCode = response?.statusCode()
            value = response?.body().toString()
        }
        Then("request finished with not success, with code {int} and message {word}") {
                code: Int, doctorMessage: String ->
            assertEquals(doctorMessage, value)
            assertEquals(code, statusCode)
        }
        And("theoretical exam pass is not being created for {word}, receiving message {word} and code {int}") {
                id: String, message: String, code: Int ->
            val request = examService
                .get("/theoreticalExam/$id/pass")
                .send()
            val response = sleeper.waitResult(request)

            checkResponse(response)
            assertEquals(code, response?.statusCode())

            // Cause if status code is 200, exam service returns the founded theoretical exam pass for D1
            // previously created
            if (response?.statusCode() != HTTP_OK) {
                assertEquals(message, response?.body().toString())
            }
        }
        Given("theoretical exam pass for dossier {word}, secretary requests to delete it") {
                dossierId: String ->
            val request = examService
                .delete("/theoreticalExam/$dossierId/pass")
                .send()
            val response = sleeper.waitResult(request)

            checkResponse(response)
            statusCode = response?.statusCode()
            value = response?.body().toString()
        }
        Then("it receives code {int} with message {word}") { code: Int, message: String ->
            assertEquals(code, statusCode)
            assertEquals(message, value)
        }
    }
}
