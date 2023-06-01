package dsdms.doctor.model.valueObjects

import kotlinx.serialization.Serializable

@Serializable
data class DoctorApprovalEvent(val dossierId: String, val date: String)
