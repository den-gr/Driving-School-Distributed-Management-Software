package dsdms.doctor.model.domainServices

import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.DoctorResult
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots

interface DoctorService {

    /**
     * @param documents: the new doctor slot to be verified before registering
     * @throws error 400 for: NOT_DOCTOR_DAY, BAD_TIME and DOSSIER_ALREADY_BOOKED
     * @throws error 200 for OK
     * @return specific domain response status for each error case
     */
    suspend fun verifyDocuments(documents: DoctorSlot): DomainResponseStatus

    /**
     * @see verifyDocuments before calling this method
     * @param documents: the new doctor slot to be registered
     * @return the date in which the doctor slot has been put
     */
    suspend fun saveDoctorSlot(documents: DoctorSlot): String

    /**
     * @param data: containing the date for which we took occupied driving slots
     * @return list of possible occupied doctor slots in a provided date
     */
    suspend fun getOccupiedDoctorSlots(data: GetBookedDoctorSlots): List<DoctorSlot>

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