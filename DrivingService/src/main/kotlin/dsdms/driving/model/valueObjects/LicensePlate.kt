package dsdms.driving.model.valueObjects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * License plate of driving school vehicle.
 * @param numberPlate of vehicle
 */
@Serializable
@SerialName("licensePlate")
data class LicensePlate(val numberPlate: String) {

    init {
        require(!checkLicensePlateFormat(numberPlate)) {"License plate format not valid"}
    }

    private fun checkLicensePlateFormat(numberPlate: String): Boolean {
        return numberPlate.matches(Regex("[A-Z]{2}[0-9]{3}[A-Z]{2}"))
    }
}
