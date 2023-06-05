package dsdms.doctor.database

import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.DoctorResult
import java.time.LocalDate

/**
 * Layer between domain and data storage.
 */
interface Repository {

    /**
     * @param doctorSlot: new doctor slot to be inserted
     * @return the date for which the new document has been inserted
     */
    suspend fun saveDoctorSlot(doctorSlot: DoctorSlot): String

    /**
     * @param date: the date when we want to obtain occupied doctor slots
     * @return lists of occupied driving slots in a certain date
     */
    suspend fun getOccupiedDoctorSlots(date: String): List<DoctorSlot>

    /**
     * @since each dossier can book only one doctor slot
     * @param dossierId: the id of a dossier
     * @return a repository response status: OK or DELETE_ERROR
     */
    suspend fun deleteDoctorSlot(dossierId: String): RepositoryResponseStatus

    /**
     * @param dossierId: the id of a specific dossier
     * @param today: optional parameters to take all doctor slots only after given date
     * @return a list of doctor slots booked by the dossierId (eventually filtered by date)
     */
    suspend fun getAllDoctorSlots(dossierId: String, today: LocalDate? = null): List<DoctorSlot>

    /**
     * @param document: doctor result for a specific dossier id
     * @return Repository response status: OK or INSERT_ERROR
     */
    suspend fun registerDoctorResult(document: DoctorResult): RepositoryResponseStatus
}
