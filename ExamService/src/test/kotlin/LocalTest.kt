import com.mongodb.client.MongoCollection
import dsdms.exam.database.Repository
import dsdms.exam.database.RepositoryImpl
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.litote.kmongo.*
import kotlin.test.assertNotNull

class LocalTest {
    lateinit var collection: MongoCollection<ProvisionalLicenseHolder>
    lateinit var repo: Repository

    @BeforeEach
    fun beforeEach() {
        val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
        val database = client.getDatabase("exam_service")
        repo = RepositoryImpl(database)
        collection = database.getCollection<ProvisionalLicenseHolder>("ProvisionalLicenseHolders")
    }

    @Disabled
    @Test
    fun mytest(){
        val cjson = Json{
            encodeDefaults = true
        }
        val obj = repo.findProvisionalLicenseHolder("64775c584c62e63454f6697a")!!
        println(cjson.encodeToString(obj))
        println(obj.provisionalLicense.endValidity)
        assertNotNull(obj)
    }
}