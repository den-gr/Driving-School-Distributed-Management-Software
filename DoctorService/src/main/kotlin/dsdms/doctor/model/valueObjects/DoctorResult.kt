package dsdms.doctor.model.valueObjects

import kotlinx.serialization.Serializable

/**
 * Doctor visit result.
 * @param dossierId
 * @param date of the visit
 * @param result doctor's visit conclusion
 */
@Serializable
data class DoctorResult(
    val dossierId: String,
    val date: String,
    val result: ResultTypes,
)

/**
 * Types of doctors results.
 */
enum class ResultTypes {
    VALID,
    NEED_ONE_MORE_VISIT,
    NOT_VALID,
}
