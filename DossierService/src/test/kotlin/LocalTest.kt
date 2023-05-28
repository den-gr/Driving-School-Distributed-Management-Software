import com.mongodb.client.MongoCollection
import dsdms.dossier.model.entities.Dossier
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.litote.kmongo.KMongo
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class LocalTest {
    lateinit var dossiers: MongoCollection<Dossier>

    @BeforeEach
    fun beforeEach() {
        val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
        val database = client.getDatabase("dossier_service")
        dossiers = database.getCollection<Dossier>("Dossier")
    }

    @Disabled
    @Test
    fun assertInsertTest() {
        val den = Dossier("den", "grush", "1999-03-07","DNFG123")
        val result: Dossier = den.apply { dossiers.insertOne(den) }

        assertNull(result)
    }

    @Disabled
    @Test
    fun updateTest() {
        val den = Dossier("den", "grush", "1999-03-07","DNFG123456789")
        val result: Dossier = den.apply { dossiers.insertOne(den) }

        println(result.toString())

        val updateResult = dossiers.deleteOne(Dossier::_id eq "12345678OGP")

        println(updateResult.toString())

        assertTrue(updateResult.deletedCount.toInt() != 1)
    }

    val mjson = Json{
        encodeDefaults = true
    }

    @Disabled
    @Test
    fun findOne(){
        val dos = dossiers.findOne(Dossier::_id eq "d2")
        println(dos)
        val ser = mjson.encodeToString(dos)
        println("\nserialize ${mjson.encodeToString(dos)}")
        println("\ndeserialize ${mjson.decodeFromString<Dossier>(ser)}")
        assertTrue(true)
    }

    @Test
    fun deserializationTest(){
        val dossierJson = """{"name":"Homer","surname":"Simpsons","birthdate":"1990-03-03","fiscal_code":"SMPHMR80A01C573O","_id":"64633c11f85fe95eb801c0b6","validity":true,"examsStatus":{"theoreticalExamState":"TO_DO","practicalExamState":"TO_DO"}}"""
        val dossier: Dossier? = mjson.decodeFromString(dossierJson)
        assertEquals(dossierJson, mjson.encodeToString(dossier))
    }
}
