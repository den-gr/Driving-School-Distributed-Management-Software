package dsdms.driving.database.mock

import dsdms.driving.database.utils.RepositoryResponseStatus
import kotlinx.datetime.LocalDate

/**
 * TODO: this will call ExamService APi (like cucumber tests)
 *      For now i simply call these methods to mock drivingService implementation
 */
class ExamService {
    private val provisionalLicenses: List<ProvisionalLicense> = mutableListOf(
        ProvisionalLicense("1", "d1", "2024-01-23"),
        ProvisionalLicense("2", "d2", "2024-01-25"),
        ProvisionalLicense("3", "d3", "2024-01-23"),
        ProvisionalLicense("4", "d4", "2024-01-25"),
        ProvisionalLicense("5", "d5", "2024-01-23"),
        ProvisionalLicense("6", "d6", "2023-12-20")
    )

    fun getProvisionalLicenseInfo(dossierId: String, date: LocalDate): RepositoryResponseStatus {
        val result: ProvisionalLicense? = provisionalLicenses.find{ el -> el.dossierId == dossierId}
        return if (result == null)
            RepositoryResponseStatus.NO_PROVISIONAL_LICENSE
        else
            if (checkValidity(date, LocalDate.parse(result.expiryDate)))
                RepositoryResponseStatus.OK
            else RepositoryResponseStatus.INVALID_PROVISIONAL_LICENSE
    }

    private fun checkValidity(drivingSlotDate: LocalDate, expiryDate: LocalDate): Boolean {
        val result = drivingSlotDate.compareTo(expiryDate)
        return result < 0
    }
}

data class ProvisionalLicense(
    val id: String,
    val dossierId: String,
    val expiryDate: String
)