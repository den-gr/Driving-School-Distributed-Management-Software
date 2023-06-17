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
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.DrivingSlotType
import dsdms.driving.model.valueObjects.LicensePlate
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals

/**
 * Regular driving slot test implementations.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/driving/drivingSlotBooking.feature"],
    plugin = ["pretty", "summary"],
)
class RegularDrivingSlotTest : En {
    private val client: WebClient = VertxProviderImpl().getDrivingServiceClient()
    private var value: List<DrivingSlot>? = null
    private var statusCode: Int? = null
    private var statusMessage: String? = null
    private var registeredSlot: String? = null

    private val sleeper = SmartSleep()

    init {

        When("i send {word}, {word}, {word}, {word}, {word} to book the driving slot") {
                date: String, time: String, instructorId: String, dossierId: String, vehicle: String ->
            val request = client
                .post("/drivingSlots")
                .sendBuffer(
                    createJson(
                        DrivingSlotBooking(
                            LocalDate.parse(date),
                            LocalTime.parse(time),
                            instructorId,
                            dossierId,
                            DrivingSlotType.ORDINARY,
                            LicensePlate(vehicle),
                        ),
                    ),
                )
            val response = sleeper.waitResult(request)
            checkResponse(response)
            statusMessage = response?.body().toString()
            statusCode = response?.statusCode()
        }
        /**
         * Status code must be different from HTTP_OK
         * If test returns HTTP_OK, it returns the ID of the booked driving slot
         */
        Then("i receive {word} with {int}") { response: String, code: Int ->
            if (statusCode != HTTP_OK) {
                assertEquals(response, statusMessage)
            }
            assertEquals(code, statusCode)
        }

        When("i request occupied driving slots in a {word}") { date: String ->
            val request = client
                .get("/drivingSlots?date=$date").send()
            val response = sleeper.waitResult(request)

            checkResponse(response)
            val statusCode = response?.statusCode()
            assertEquals(HTTP_OK, statusCode)
            value = Json.decodeFromString(ListSerializer(DrivingSlot.serializer()), response?.body().toString())
        }
        Then(
            "the first driving slot is: {word}, time {word}, instructor id {word}, dossier id {word}, vehicle {word}",
        ) {
                date: String, time: String, instructorId: String, dossierId: String, vehicle: String ->
            assertEquals(date, value?.get(0)?.date.toString())
            assertEquals(time, value?.get(0)?.time.toString())
            assertEquals(instructorId, value?.get(0)?.instructorId)
            assertEquals(dossierId, value?.get(0)?.dossierId)
            assertEquals(vehicle, value?.get(0)?.licensePlate?.numberPlate)
        }
        Then(
            "the second driving slot is: {word}, time {word}, instructor id {word}, dossier id {word}, vehicle {word}",
        ) {
                date: String, time: String, instructorId: String, dossierId: String, vehicle: String ->
            assertEquals(date, value?.get(1)?.date.toString())
            assertEquals(time, value?.get(1)?.time.toString())
            assertEquals(instructorId, value?.get(1)?.instructorId)
            assertEquals(dossierId, value?.get(1)?.dossierId)
            assertEquals(vehicle, value?.get(1)?.licensePlate?.numberPlate)
        }
        Then(
            "the third driving slot is: {word}, time {word}, instructor id {word}, dossier id {word}, vehicle {word}",
        ) {
                date: String, time: String, instructorId: String, dossierId: String, vehicle: String ->
            assertEquals(date, value?.get(2)?.date.toString())
            assertEquals(time, value?.get(2)?.time.toString())
            assertEquals(instructorId, value?.get(2)?.instructorId)
            assertEquals(dossierId, value?.get(2)?.dossierId)
            assertEquals(vehicle, value?.get(2)?.licensePlate?.numberPlate)
        }

        When("i send {word}, {word}, {word}, {word}, {word} to book the wrong driving slot") {
                date: String, time: String, instructorId: String, dossierId: String, vehicle: String ->
            val request = client
                .post("/drivingSlots")
                .sendBuffer(
                    createJson(
                        DrivingSlotBooking(
                            LocalDate.parse(date),
                            LocalTime.parse(time),
                            instructorId,
                            dossierId,
                            DrivingSlotType.ORDINARY,
                            LicensePlate(vehicle),
                        ),
                    ),
                )
            val response = sleeper.waitResult(request)
            checkResponse(response)
            registeredSlot = response?.body().toString()
            statusCode = response?.statusCode()
        }
        Given("the id of the inserted driving slot") {
            assertEquals(HTTP_OK, statusCode)
        }
        When("attempting to remove it, i receive code {int}") { code: Int ->
            val response = deleteSlot()
            statusMessage = response.body().toString()
            statusCode = response.statusCode()

            assertEquals(code, statusCode)
        }
        Then("when attempting to remove it another time \\(wrongly), i receive code {int} with {word}") {
                code: Int, message: String ->
            val response = deleteSlot()
            statusMessage = response.body().toString()
            statusCode = response.statusCode()

            assertEquals(message, statusMessage)
            assertEquals(code, statusCode)
        }
    }

    private fun deleteSlot(): HttpResponse<Buffer> {
        val request = client
            .delete("/drivingSlots/$registeredSlot").send()
        val response = sleeper.waitResult(request)
        checkResponse(response)
        return response!!
    }
}
