package dsdms.dossier.model.valueObjects.examStatus

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable

@Serializable
@JsonDeserialize(`as` = ExamStatusImpl::class)
sealed interface ExamStatus {
    fun modifyTheoretical(newStatus: Boolean)

    fun modifyPractical(newStatus: Boolean)

    fun getPractical(): Boolean

    fun getTheoretical(): Boolean
}
