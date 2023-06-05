package dsdms.exam.model.entities

import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.serialization.Serializable

/**
 * Represents the provisional license.
 * @see ProvisionalLicenseHolder
 * @param dossierId
 * @param startValidity -> the starting date for provisional license, ends in 1 year or 3 failed exam attempts
 */
@Serializable
data class ProvisionalLicense(val dossierId: String, val startValidity: LocalDate) {

    /**
     * End validity date for this provisional license.
     * Default set to 1 year after.
     */
    val endValidity: LocalDate
    init {
        endValidity = startValidity + DatePeriod(years = 1)
    }
}
