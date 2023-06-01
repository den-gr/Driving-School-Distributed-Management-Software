package dsdms.exam.model.entities

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.serialization.Serializable
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

/**
 * Represents the provisional license
 * @see ProvisionalLicenseHolder
 * @param dossierId
 * @param startValidity -> the starting date for provisional license, ends in 1 year or 3 failed exam attempts
 */
@Serializable
data class ProvisionalLicense(val dossierId: String, val startValidity: LocalDate) {
    val endValidity: LocalDate
    init {
        endValidity = startValidity + DatePeriod(years = 1)
    }
}