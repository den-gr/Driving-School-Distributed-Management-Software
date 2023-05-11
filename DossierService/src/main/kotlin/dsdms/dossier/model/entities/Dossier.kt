package dsdms.dossier.model.entities

import dsdms.dossier.model.valueObjects.examAttempts.PracticalExamAttempts
import dsdms.dossier.model.valueObjects.examAttempts.PracticalExamAttemptsImpl
import dsdms.dossier.model.valueObjects.examStatus.ExamStatus
import dsdms.dossier.model.valueObjects.examStatus.ExamStatusImpl
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Dossier(
    val name: String,
    val surname: String,
    val birthdate: String,
    val fiscal_code: String,
    @Contextual val _id: String? = null,
    val validity: Boolean = true,
    val examAttempts: PracticalExamAttempts = PracticalExamAttemptsImpl(),
    val examStatus: ExamStatus = ExamStatusImpl()
)
