import com.mongodb.client.MongoCollection
import dsdms.dossier.model.Dossier
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.string.shouldHaveLength
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class InsertTest: FunSpec({
    var dossiers: MongoCollection<Dossier>? = null

    beforeEach {
        val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
        val database = client.getDatabase("dossier_service")
        dossiers = database.getCollection<Dossier>("Dossier")
    }

    test("assert 0 equals 0") {
        val den = Dossier("den", "grush", "DNFG123")
        val result: Dossier = den.apply { dossiers?.insertOne(den) }

        println(result.toString())
        "sam".shouldHaveLength(3)
    }
})