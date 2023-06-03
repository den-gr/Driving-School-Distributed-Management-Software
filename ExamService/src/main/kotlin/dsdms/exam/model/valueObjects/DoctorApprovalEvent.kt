package dsdms.exam.model.valueObjects

import kotlinx.serialization.Serializable

/**
 * Data needed to create a new theoretical exam pass
 * @param dossierId -> id of a specific dossier
 * @param date -> date in which the doctor visit has been passed, so the theoretical exam pass is created
 */
@Serializable
data class DoctorApprovalEvent(
    val dossierId: String,
    val date: String
)
