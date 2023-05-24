package dsdms.driving.model.valueObjects.licensePlate

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("licensePlate")
class LicensePlateImpl : LicensePlate {

    @JsonProperty
    var numberPlate: String

    @JsonCreator
    constructor(numberPlate: String) {
        if (!verifyStructure(numberPlate)) {
            throw IllegalArgumentException("License plate format not valid")
        }
        this.numberPlate = numberPlate
    }

    override fun verifyStructure(numberPlate: String): Boolean {
        val regex = Regex("[A-Z]{2}[0-9]{3}[A-Z]{2}")
        return numberPlate.matches(regex)
    }

    override fun toString(): String {
        return numberPlate
    }
}
