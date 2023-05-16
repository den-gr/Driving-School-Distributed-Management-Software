package dsdms.doctor.model.domainServices

import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots

interface DoctorService {

    /**
     * Verify that wanted date is a DoctorDay, wanted time is between doctor init
     * and finish time and that in the wanted day, the wanted time is available
     * @return specific domain response status for each error case
     */
    fun verifyDocuments(documents: DoctorSlot): DomainResponseStatus

    /**
     * @return the date in which the doctor slot has been put
     */
    fun saveDoctorSlot(documents: DoctorSlot): String

    /**
     * @return list of possible occupied doctor slots in a provided date
     */
    fun getOccupiedDoctorSlots(data: GetBookedDoctorSlots): List<DoctorSlot>

    /**
     * @return Domain response status: OK if delete was successful, DELETE_ERROR otherwise
     */
    fun deleteDoctorSlot(dossierId: String): DomainResponseStatus
}