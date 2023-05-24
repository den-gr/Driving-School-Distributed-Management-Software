package dsdms.doctor.model.valueObjects

import kotlinx.serialization.Serializable

@Serializable
data class DoctorResult(
    val dossierId: String,
    val date: String,
    val result: ResultTypes
)

enum class ResultTypes {
    VALID,
    NEED_ONE_MORE_VISIT,
    NOT_VALID
}