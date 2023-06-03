package dsdms.doctor.model.domainServices

import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.DoctorResult

/**
 * Doctor context operational domain logic.
 */
interface DoctorService {

    /**
     * @param documents: the new doctor slot to be registered
     * @return the date in which the doctor slot has been put
     */
    suspend fun saveDoctorSlot(documents: DoctorSlot): InsertDoctorVisitResult

    /**
     * @param date: containing the date for which we took occupied driving slots
     * @return list of possible occupied doctor slots in a provided date
     */
    suspend fun getOccupiedDoctorSlots(date: String): BookedDoctorSlots

    /**
     * @param dossierId: id for which to take occupied driving slots
     * @return Domain response status: OK if delete was successful, DELETE_ERROR otherwise
     */
    suspend fun deleteDoctorSlot(dossierId: String): DomainResponseStatus

    /**
     * @param document
     * @return Domain Response Status:
     *  - OK if result was acknowledge
     *  - INSERT_ERROR otherwise
     */
    suspend fun saveDoctorResult(document: DoctorResult): DomainResponseStatus
}
