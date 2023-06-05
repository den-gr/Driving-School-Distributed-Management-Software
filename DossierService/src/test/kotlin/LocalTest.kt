import dsdms.dossier.model.entities.Dossier
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

internal class LocalTest {
    val mjson = Json {
        encodeDefaults = true
    }

    @Test
    fun deserializationTest() {
        val dossierJson = """{"name":"Homer","surname":"Simpsons","birthdate":"1990-03-03",
            "fiscal_code":"SMPHMR80A01C573O","_id":"64633c11f85fe95eb801c0b6","validity":true,
            "examsStatus":{"theoreticalExamState":"TO_DO","practicalExamState":"TO_DO"}}"""
            .trimIndent().replace("\\s".toRegex(), "")
        val dossier: Dossier? = mjson.decodeFromString(dossierJson)

        val actualJson = mjson.encodeToString(dossier)
        println(actualJson)
        assertEquals(dossierJson, actualJson)
    }
}
