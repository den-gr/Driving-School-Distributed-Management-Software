package dsdms.exam.model.valueObjects

import kotlinx.serialization.Serializable

@Serializable
enum class ExamEvent {
    THEORETICAL_EXAM_PASSED, PROVISIONAL_LICENSE_INVALIDATION, PRACTICAL_EXAM_PASSED
}
