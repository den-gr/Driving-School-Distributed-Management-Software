package dsdms.driving.model.entities

import dsdms.driving.model.valueObjects.licensePlate.LicensePlate
import kotlinx.serialization.Serializable

/**
 * Represents a single vehicle
 * @param licensePlate identifies a single vehicle
 * @see LicensePlate
 */
@Serializable
data class Vehicle(
    val licensePlate: LicensePlate,
    val manufacturer: String,
    val model: String,
    val year: Int
)