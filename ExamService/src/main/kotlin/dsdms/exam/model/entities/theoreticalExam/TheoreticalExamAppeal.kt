package dsdms.exam.model.entities.theoreticalExam

import kotlinx.serialization.Serializable

/**
 * Represents a theoretical exam appeal.
 * @param date of the exam appeal
 * @param numberOfPlaces how many subscribers can be register in the appeal
 * @param registeredDossiers -> list of registered dossiers into this exam, set by default to empty list
 * @param initTime -> set by default to 10:00
 * @param finishTime -> set by default to 13:00
 */
@Serializable
data class TheoreticalExamAppeal(
    val date: String,
    val numberOfPlaces: Int,
    val registeredDossiers: List<String> = listOf(),
    val initTime: String = "10:00",
    val finishTime: String = "13:00",
)
