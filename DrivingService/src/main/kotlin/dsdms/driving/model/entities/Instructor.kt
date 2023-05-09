package dsdms.driving.model.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class Instructor(
    val name: String,
    val surname: String,
    val fiscal_code: String,
    @Contextual val _id: String? = null
)