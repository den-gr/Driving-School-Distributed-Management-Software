package dsdms.exam.model.entities.theoreticalExam

import kotlinx.serialization.Serializable

@Serializable
data class TheoreticalExamDay(
    val date: String,
    val numberOfPlaces: Int,
    val initTime: String = "10:00",
    val finishTime: String = "13:00"
)