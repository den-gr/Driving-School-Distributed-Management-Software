import com.mongodb.client.MongoCollection
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.examStatus.ExamStatus
import dsdms.dossier.model.valueObjects.examStatus.ExamStatusImpl
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.litote.kmongo.*

@Suppress("unused")
class LocalTest: FunSpec({
    lateinit var dossiers: MongoCollection<Dossier>

    beforeEach {
        val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
        val database = client.getDatabase("dossier_service")
        dossiers = database.getCollection<Dossier>("Dossier")
    }

    test("assert insert test") {
        val den = Dossier("den", "grush", "DNFG123")
        val result: Dossier = den.apply { dossiers.insertOne(den) }

        result shouldNotBe null
    }

    test("update test") {
        val den = Dossier("den", "grush", "DNFG123456789")
        val result: Dossier = den.apply { dossiers.insertOne(den) }

        println(result.toString())

        val newStatus: ExamStatus = ExamStatusImpl()
        newStatus.modifyTheoretical(true)

        val updateResult = dossiers.deleteOne(Dossier::_id eq "12345678OGP")

        println(updateResult.toString())

        (updateResult.deletedCount.toInt() != 1) shouldBe true
    }
})