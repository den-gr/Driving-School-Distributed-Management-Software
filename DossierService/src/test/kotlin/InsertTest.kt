import com.mongodb.client.MongoCollection
import dsdms.dossier.model.Dossier
import dsdms.dossier.model.examStatus.ExamStatus
import dsdms.dossier.model.examStatus.ExamStatusImpl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldHaveLength
import org.litote.kmongo.*
import kotlin.test.assertEquals

class InsertTest: FunSpec({
    lateinit var dossiers: MongoCollection<Dossier>

    beforeEach {
        val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
        val database = client.getDatabase("dossier_service")
        dossiers = database.getCollection<Dossier>("Dossier")
    }

    test("assert 0 equals 0") {
        val den = Dossier("den", "grush", "DNFG123")
        val result: Dossier = den.apply { dossiers.insertOne(den) }

        println(result.toString())
        "sam".shouldHaveLength(3)
    }

    test("update test") {
        val den = Dossier("den", "grush", "DNFG123456789")
        val result: Dossier = den.apply { dossiers.insertOne(den) }

        println(result.toString())

        val newStatus: ExamStatus = ExamStatusImpl()
        newStatus.modifyTheoretical(true)

        val updateResult = dossiers.deleteOne(Dossier::_id eq "12345678OGP")

        println(updateResult.toString())

        assertEquals(true, updateResult.deletedCount.toInt() != 1)
    }
})