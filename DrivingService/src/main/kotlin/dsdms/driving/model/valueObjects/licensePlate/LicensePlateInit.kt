package dsdms.driving.model.valueObjects.licensePlate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("vehicle")
class LicensePlateInit(private val licensePlate: String): LicensePlate {

    init {
        if (!verifyStructure())
            throw IllegalArgumentException("License plate format not valid")
    }

    override fun verifyStructure(): Boolean {
        val regex = Regex("[A-Z]{2}[0-9]{3}[A-Z]{2}")
        return licensePlate.matches(regex)
    }

    override fun toString(): String {
        return licensePlate
    }

}