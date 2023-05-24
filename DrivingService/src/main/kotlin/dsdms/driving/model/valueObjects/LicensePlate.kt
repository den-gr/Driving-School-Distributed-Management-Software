package dsdms.driving.model.valueObjects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("licensePlate")
data class LicensePlate(val numberPlate: String) {

    init {
        if (!checkLicensePlateFormat(numberPlate)) {
            throw IllegalArgumentException("License plate format not valid")
        }
    }

    private fun checkLicensePlateFormat(numberPlate: String): Boolean {
        return numberPlate.matches(Regex("[A-Z]{2}[0-9]{3}[A-Z]{2}"))
    }
}
