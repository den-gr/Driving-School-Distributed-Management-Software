package dsdms.driving.model.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Represents info about instructors.
 * @param name of instructor
 * @param surname of instructor
 * @param fiscal_code of instructor
 * @param _id: auto generated instructor identifier
 */
@Serializable
data class Instructor(
    val name: String,
    val surname: String,
    val fiscal_code: String,
    @Contextual val _id: String? = null,
)
