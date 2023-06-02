package dsdms.exam.model

import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.litote.kmongo.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProvisionalLicenseTest {
    val mjson = Json{
        encodeDefaults = true
    }

    @Test
    fun checkProvisionalLicenseValidity(){
        val date = LocalDate.parse("2023-10-12")
        val provisionalLicense = ProvisionalLicense("d1", date)
        assertEquals(provisionalLicense.endValidity, LocalDate.parse("2024-10-12"))
        assertTrue(provisionalLicense.isValidOn(LocalDate.parse("2023-10-12")))
        assertTrue(provisionalLicense.isValidOn(LocalDate.parse("2023-11-12")))
        assertTrue(provisionalLicense.isValidOn(LocalDate.parse("2023-11-13")))
        assertTrue(provisionalLicense.isValidOn(LocalDate.parse("2024-10-12")))
        assertFalse(provisionalLicense.isValidOn(LocalDate.parse("2024-10-13")))
        assertFalse(provisionalLicense.isValidOn(LocalDate.parse("2023-10-11")))
    }

    @Test
    fun serialization(){
        val jsonString = """{"practicalExamAttempts":0,"provisionalLicense":{"dossierId":"d5","startValidity":"2022-11-25","endValidity":"2023-11-25"}}"""
        val holder: ProvisionalLicenseHolder? = mjson.decodeFromString(jsonString)
        println(holder)
        assertEquals(jsonString, mjson.encodeToString(holder))
    }

    @Disabled
    @Test
    fun dbtest(){
        val client = KMongo.createClient("mongodb://admin:admin@localhost:27017")
        val database = client.getDatabase("exam_service")
        val coll = database.getCollection<ProvisionalLicenseHolder>("ProvisionalLicenseHolders")
        val ris = coll.findOne(ProvisionalLicenseHolder::provisionalLicense / ProvisionalLicense::dossierId eq "d1")
        println(ris)

        assertTrue(true)
    }
}