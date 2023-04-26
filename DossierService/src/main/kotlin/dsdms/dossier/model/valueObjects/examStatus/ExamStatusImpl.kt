package dsdms.dossier.model.valueObjects.examStatus

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("examStatus")
class ExamStatusImpl : ExamStatus {
    private var theoreticalExam: Boolean = false
    private var practicalExam: Boolean = false

    override fun modifyTheoretical(newStatus: Boolean) {
        this.theoreticalExam = newStatus
    }

    override fun modifyPractical(newStatus: Boolean) {
        this.practicalExam = newStatus
    }

    override fun getPractical(): Boolean = practicalExam

    override fun getTheoretical(): Boolean = theoreticalExam
}