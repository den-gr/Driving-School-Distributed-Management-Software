package dsdms.driving.model.valueObjects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 * @param date date of the practical exam
 */
@Serializable
class PracticalExamDay(
    val date: LocalDate,
)
