package dsdms.doctor.model.valueObjects

import kotlinx.serialization.Serializable

/**
 * The event when doctor approves subscriber.
 * @param dossierId
 * @param date of approval
 */
@Serializable
data class DoctorApprovalEvent(val dossierId: String, val date: String)
