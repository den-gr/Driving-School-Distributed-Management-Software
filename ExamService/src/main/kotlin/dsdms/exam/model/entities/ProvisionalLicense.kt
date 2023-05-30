package dsdms.exam.model.entities

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.serialization.Serializable

@Serializable
data class ProvisionalLicense(val dossierId: String, val startValidity: LocalDate) {
    val endValidity: LocalDate
    init {
        endValidity = startValidity + DatePeriod(years = 1)
    }
}