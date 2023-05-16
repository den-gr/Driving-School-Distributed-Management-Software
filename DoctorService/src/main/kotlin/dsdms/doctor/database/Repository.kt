package dsdms.doctor.database

import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots

interface Repository {

    fun saveDoctorSlot(documents: DoctorSlot): String
    fun getOccupiedDoctorSlots(data: GetBookedDoctorSlots): List<DoctorSlot>
    fun deleteDoctorSlot(dossierId: String): RepositoryResponseStatus
}
