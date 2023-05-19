package dsdms.dossier.model.valueObjects.examStatus

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable

@Serializable
@JsonDeserialize(`as` = ExamStatusImpl::class)
sealed interface ExamStatus {

    /**
     * @param newStatus: new status for theoretical exam
     */
    fun modifyTheoretical(newStatus: Boolean)

    /**
     * @param newStatus: new status for practical exam
     */
    fun modifyPractical(newStatus: Boolean)

    /**
     * @return actual status for practical exam
     */
    fun getPractical(): Boolean

    /**
     * @return actual status for tehoretical exam
     */
    fun getTheoretical(): Boolean
}
