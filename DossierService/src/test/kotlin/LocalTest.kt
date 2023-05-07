import com.mongodb.client.MongoCollection
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.examStatus.ExamStatus
import dsdms.dossier.model.valueObjects.examStatus.ExamStatusImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import kotlin.test.Test
import org.litote.kmongo.*
import kotlin.test.assertNull
import kotlin.test.assertTrue

internal class LocalTest{
    lateinit var dossiers: MongoCollection<Dossier>

    @BeforeEach
    fun beforeEach() {
        val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
        val database = client.getDatabase("dossier_service")
        dossiers = database.getCollection<Dossier>("Dossier")
    }

    @Disabled
    @Test fun assertInsertTest(){
        val den = Dossier("den", "grush", "DNFG123")
        val result: Dossier = den.apply { dossiers.insertOne(den) }

        assertNull(result)
    }

    @Disabled
    @Test fun updateTest(){
        val den = Dossier("den", "grush", "DNFG123456789")
        val result: Dossier = den.apply { dossiers.insertOne(den) }

        println(result.toString())

        val newStatus: ExamStatus = ExamStatusImpl()
        newStatus.modifyTheoretical(true)

        val updateResult = dossiers.deleteOne(Dossier::_id eq "12345678OGP")

        println(updateResult.toString())

        assertTrue(updateResult.deletedCount.toInt() != 1)
    }
}