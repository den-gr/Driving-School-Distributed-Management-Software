package dsdms.doctor.model.domainServices

import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.DoctorResult

/**
 * Doctor context operational domain logic.
 */
interface DoctorDomainService {

    /**
     * @param doctorSlot: the new doctor slot to be registered
     * @return the doctor slot date
     */
    suspend fun saveDoctorSlot(doctorSlot: DoctorSlot): InsertDoctorVisitResult

    /**
     * @param date: containing the date for which we took occupied doctor slots
     * @return domain response status and possible list of occupied doctor slots in a provided date
     */
    suspend fun getOccupiedDoctorSlots(date: String): BookedDoctorSlots

    /**
     * @param dossierId: id for which to take occupied driving slots
     * @return Domain response status: OK if delete was successful, DELETE_ERROR otherwise
     */
    suspend fun deleteDoctorSlot(dossierId: String): DomainResponseStatus

    /**
     * @param doctorResult
     * @return Domain Response Status:
     *  - OK if result was acknowledge
     *  - INSERT_ERROR otherwise
     */
    suspend fun saveDoctorResult(doctorResult: DoctorResult): DomainResponseStatus
}
