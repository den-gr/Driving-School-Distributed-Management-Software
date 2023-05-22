package dsdms.dossier.model.entities

import dsdms.dossier.model.valueObjects.examAttempts.PracticalExamAttempts
import dsdms.dossier.model.valueObjects.examAttempts.PracticalExamAttemptsImpl
import dsdms.dossier.model.valueObjects.examStatus.ExamStatus
import dsdms.dossier.model.valueObjects.examStatus.ExamStatusImpl
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Represents a complete Dossier representation
 * @param name: name of subscriber
 * @param surname: surname of subscriber
 * @param birthdate: birthdate of subscriber to verify that he has the minimum age
 * @param fiscal_code: of subscriber for additional info
 * @param validity: representing validity of the dossier (true by default)
 * @param examAttempts: representing practical and theoretical exam attempts, Zero by default
 * @param examStatus: representing practical and theoretical exam status, all false by defaylt
 */
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
