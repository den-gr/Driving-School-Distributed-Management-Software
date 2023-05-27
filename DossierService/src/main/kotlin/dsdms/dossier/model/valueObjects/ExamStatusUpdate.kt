package dsdms.dossier.model.valueObjects

import kotlinx.serialization.Serializable

@Serializable
enum class Exam{
    THEORETICAL, PRACTICAL
}

enum class ExamOutcome{
    PASSED, FAIL
}
/**
 * Represents documents that need to be passed to update exam status
 * @param exam: could be THEORETICAL or PRACTICAL
 * @param outcome: could be PASSED or FAIL
 */
@Serializable
data class ExamResult(
    val exam: Exam,
    val outcome: ExamOutcome
)