package dsdms.exam.model.valueObjects

import kotlinx.serialization.Serializable

/**
 * Used to add ad dossier id into a specific exam appeal
 * @param date -> date for a theoretical exam appeal
 * @param dossierId -> id of a specific dossier
 */
@Serializable
data class TheoreticalExamAppealUpdate(
    val dossierId: String,
    val date: String
)