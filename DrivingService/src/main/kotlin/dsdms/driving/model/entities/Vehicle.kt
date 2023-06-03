package dsdms.driving.model.entities

import dsdms.driving.model.valueObjects.LicensePlate
import kotlinx.serialization.Serializable

/**
 * Represents a vehicle.
 * @param licensePlate
 * @param manufacturer
 * @param model
 * @param year
 */
@Serializable
data class Vehicle(
    val licensePlate: LicensePlate,
    val manufacturer: String,
    val model: String,
    val year: Int,
)
