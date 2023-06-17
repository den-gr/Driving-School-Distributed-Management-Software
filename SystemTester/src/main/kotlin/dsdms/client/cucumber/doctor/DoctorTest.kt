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

package dsdms.client.cucumber.doctor

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.client.utils.createJson
import dsdms.doctor.model.entities.DoctorSlot
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.ext.web.client.WebClient
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Doctor tests implementation.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/main/resources/features/doctor/doctorTest.feature"],
    plugin = ["pretty", "summary"],
)
class DoctorTest : En {
    private val client: WebClient = VertxProviderImpl().getDoctorServiceClient()
    private var receivedCode: Int? = null
    private var receivedMessage: String = ""

    init {
        val sleeper = SmartSleep()
        When(
            "sending {word} for registering doctor visit on {word} and {word}",
        ) { id: String, date: String, time: String ->
            val request = client
                .post("/doctorSlots")
                .sendBuffer(createJson(DoctorSlot(date, time, id)))
            val response = sleeper.waitResult(request)

            checkResponse(response)

            receivedCode = response?.statusCode()
            receivedMessage = response?.body().toString()
        }
        Then("secretary receives {word} with {int}") { message: String, code: Int ->
            assertEquals(message, receivedMessage)
            assertEquals(code, receivedCode)
        }

        When("it sends {word}") { data: String ->
            val request = client
                .get("/doctorSlots/$data").send()
            val response = sleeper.waitResult(request)

            checkResponse(response)

            receivedCode = response?.statusCode()
            receivedMessage = response?.body().toString()
        }
        Then("receives list of doctor slots, containing one doctor slot for dossier {word} at {word}") {
                id: String, time: String ->
            assertEquals(HTTP_OK, receivedCode)
            val doctorSlots: List<DoctorSlot> =
                Json.decodeFromString(ListSerializer(DoctorSlot.serializer()), receivedMessage)
            assertTrue(doctorSlots.isNotEmpty())
            assertEquals(1, doctorSlots.size)
            assertEquals(DoctorSlot("2023-09-19", time, id), doctorSlots[0])
        }
    }
}
