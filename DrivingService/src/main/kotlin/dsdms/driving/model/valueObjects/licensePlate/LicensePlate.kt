package dsdms.driving.model.valueObjects.licensePlate

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable

@Serializable
@JsonDeserialize(`as` = LicensePlateInit::class)
sealed interface LicensePlate {
    fun verifyStructure(numberPlate: String): Boolean
}
