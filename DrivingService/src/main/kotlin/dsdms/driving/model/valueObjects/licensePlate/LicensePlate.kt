package dsdms.driving.model.valueObjects.licensePlate

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import kotlinx.serialization.Serializable

/**
 * Representing a singular license plate
 * A license plate identifies a single vehicle
 */
@Serializable
@JsonDeserialize(`as` = LicensePlateInit::class)
sealed interface LicensePlate {

    /**
     * @param numberPlate: the new number plate to be verified
     * @return true if the given string respects a specific Regex
     */
    fun verifyStructure(numberPlate: String): Boolean
}
