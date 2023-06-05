package dsdms.exam.model.valueObjects

import kotlinx.serialization.Serializable

/**
 * Messages types to update exam status for a specific dossier.
 * Sent by exam service to dossier service.
 */
@Serializable
enum class ExamEvent {
    THEORETICAL_EXAM_PASSED, PROVISIONAL_LICENSE_INVALIDATION, PRACTICAL_EXAM_PASSED
}
