package dsdms.dossier.model

import dsdms.dossier.model.examAttempts.PracticalExamAttempts
import dsdms.dossier.model.examAttempts.PracticalExamAttemptsImpl
import dsdms.dossier.model.examStatus.ExamStatus
import dsdms.dossier.model.examStatus.ExamStatusImpl
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * fiscal code is implicitly considered as correct
 */
@Serializable
data class Dossier(
    val name: String,
    val surname: String,
    val fiscal_code: String, val validity: Boolean = true,
    @Contextual val _id: String? = null, val examAttempts: PracticalExamAttempts = PracticalExamAttemptsImpl(), val examStatus: ExamStatus = ExamStatusImpl()
)

@Serializable
data class SubscriberDocuments(val name: String,
                               val surname: String, val fiscal_code: String)

@Serializable
data class ExamStatusUpdate(val exam: String,
                               val newStatus: Boolean)
