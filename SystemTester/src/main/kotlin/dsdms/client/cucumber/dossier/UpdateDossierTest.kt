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

package dsdms.client.cucumber.dossier

import dsdms.client.utils.SmartSleep
import dsdms.client.utils.VertxProviderImpl
import dsdms.client.utils.checkResponse
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamEvent
import dsdms.dossier.model.valueObjects.PracticalExamState
import dsdms.dossier.model.valueObjects.TheoreticalExamState
import io.cucumber.java8.En
import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.HttpResponse
import io.vertx.ext.web.client.WebClient
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.runner.RunWith
import java.net.HttpURLConnection.HTTP_OK
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

/**
 * Update dossier test implementation.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["src/test/resources/features/dossier/updateDossier.feature"],
    plugin = ["pretty", "summary"],
)
class UpdateDossierTest : En {
    private val client: WebClient = VertxProviderImpl().getDossierServiceClient()
    private var dossier: String = ""
    private var retrievedDossier: Dossier? = null
    private var result: HttpResponse<Buffer>? = null
    private val sleeper = SmartSleep()

    init {

        Given("an already registered dossier with id {word}") { dossierId: String ->
            dossier = dossierId
            retrievedDossier = getDossier()
        }

        When("i read his {word} exam progress state is {word}") { type: String, state: String ->
            assertNotNull(retrievedDossier)
            if (type == ExamEvent.THEORETICAL_EXAM_PASSED.name) {
                retrievedDossier?.examsStatus
                    ?.let { assertEquals(TheoreticalExamState.valueOf(state), it.theoreticalExamState) }
            } else {
                retrievedDossier?.examsStatus
                    ?.let { assertEquals(PracticalExamState.valueOf(state), it.practicalExamState) }
            }
        }

        Then("trying to register {word} exam state as passed") { type: String ->
            val request = client
                .put("/dossiers/$dossier/examStatus")
                .sendBuffer(Buffer.buffer(type))
            val response = sleeper.waitResult(request)

            checkResponse(response)
            result = response
        }

        And("obtaining {word} exam state equal to {word}") { type: String, newState: String ->
            assertNotNull(result)
            assertEquals(HTTP_OK, result?.statusCode())

            retrievedDossier = getDossier()
            if (type == ExamEvent.THEORETICAL_EXAM_PASSED.name) {
                retrievedDossier?.examsStatus
                    ?.let { assertEquals(TheoreticalExamState.valueOf(newState), it.theoreticalExamState) }
            } else {
                retrievedDossier?.examsStatus
                    ?.let { assertEquals(PracticalExamState.valueOf(newState), it.practicalExamState) }
            }
        }
    }

    private fun getDossier(): Dossier {
        val request = client.get("/dossiers/$dossier").send()
        val response = sleeper.waitResult(request)
        checkResponse(response)
        return Json.decodeFromString(response?.body().toString())
    }
}
