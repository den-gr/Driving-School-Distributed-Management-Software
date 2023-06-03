package dsdms.driving.model.valueObjects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
class PracticalExamDay(
    val date: LocalDate,
)
