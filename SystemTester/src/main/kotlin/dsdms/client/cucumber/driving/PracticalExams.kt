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

package dsdms.client.cucumber.driving

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.client.utils.createJson
import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.DrivingSlotType
import dsdms.driving.model.valueObjects.LicensePlate
import dsdms.driving.model.valueObjects.PracticalExamDay
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.Future
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection
import kotlin.test.assertEquals

/**
 * Practical exam test implementation.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/driving/practicalExam.feature"],
    plugin = ["pretty", "summary"],
)
class PracticalExams : En {
    private val client: WebClient = VertxProviderImpl().getDrivingServiceClient()
    private var statusCode: Int? = null
    private var body: String? = null
    private var practicalExamDays: List<PracticalExamDay>? = null

    init {
        val sleeper = SmartSleep()

        Given(
            "an attempt to book a new practical exam on {word} for instructor {word}, dossier {word} and auto {word}",
        ) {
                date: String, instructorId: String, dossierId: String, auto: String ->
            val bookingRequest = DrivingSlotBooking(
                LocalDate.parse(date),
                LocalTime.parse("09:00"),
                instructorId,
                dossierId,
                DrivingSlotType.EXAM,
                LicensePlate(auto),
            )

            val request = client
                .post("/drivingSlots")
                .sendBuffer(createJson(bookingRequest))
            val response = sleeper.waitResult(request)
            checkResponse(response)
            body = response?.body().toString()
            statusCode = response?.statusCode()
        }
        Then("receive the {word} response") { errorType: String ->
            assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, statusCode)
            assertEquals(errorType, body)
        }

        /* Check NOT_AN_EXAM_DAY*/
        Given("dossier {word} has already {int} driving lessons in the past") {
                dossierId: String, numLessions: Int ->
            var localRequest: Future<HttpResponse<Buffer>>? = null
            for (i in 0 until numLessions) {
                val bookingRequest = DrivingSlotBooking(
                    LocalDate.parse("2023-01-01"),
                    LocalTime.parse("0$i:00"),
                    "i1",
                    dossierId,
                    DrivingSlotType.ORDINARY,
                    LicensePlate("KF037MF"),
                )
                localRequest = client
                    .post("/drivingSlots")
                    .sendBuffer(createJson(bookingRequest))
            }
            if (localRequest != null) {
                sleeper.waitResult(localRequest)
            }
        }

        /* Try to book a practical exam in not the practical exam day*/
        // Reuse previous scenario step
        Then("receive the {word} response for this day") { errorType: String ->
            assertEquals(HttpURLConnection.HTTP_BAD_REQUEST, statusCode)
            assertEquals(errorType, body)
        }

        When("registering a new practical exam day on {word}") { date: String ->
            val request = client
                .post("/practicalExamDays")
                .sendBuffer(createJson(PracticalExamDay(LocalDate.parse(date))))
            val response = sleeper.waitResult(request)
            checkResponse(response)
            body = response?.body().toString()
            statusCode = response?.statusCode()
        }
        Then("it receives {int} with {word}") { code: Int, message: String ->
            assertEquals(code, statusCode)
            assertEquals(message, body)
        }

        When("request a list of practical exam days") {
            val request = client.get("/practicalExamDays").send()
            val response = sleeper.waitResult(request)
            checkResponse(response)
            statusCode = response?.statusCode()
            practicalExamDays = Json.decodeFromString(
                ListSerializer(PracticalExamDay.serializer()),
                response?.body().toString(),
            )
        }
        Then("finds only one available practical exam day on {word}") { date: String ->
            assertEquals(1, practicalExamDays?.size)
            assertEquals(practicalExamDays?.get(0)?.date, LocalDate.parse(date))
        }

        /* Final successful booking scenario. It reuse the first scenario step*/
        Then("receive confirmation of successful booking") {
            assertEquals(HttpURLConnection.HTTP_OK, statusCode)
        }
    }
}
