package dsdms.exam.model

import dsdms.exam.model.entities.ProvisionalLicense
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.assertTrue

class ProvisionalLicenseTest {
    @Test
    fun testa(){
        val date = LocalDate.parse("2023-10-12")
        println(date)
        val date2 = date + DatePeriod(years = 1)
        println(date2)
        assertTrue(true)

    }
}