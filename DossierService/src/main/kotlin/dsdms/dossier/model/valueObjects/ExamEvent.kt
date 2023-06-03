package dsdms.dossier.model.valueObjects

import kotlinx.serialization.Serializable

/**
 * Types of events that can arrive from ExamContext.
 */
@Serializable
enum class ExamEvent {
    THEORETICAL_EXAM_PASSED, PROVISIONAL_LICENSE_INVALIDATION, PRACTICAL_EXAM_PASSED
}
