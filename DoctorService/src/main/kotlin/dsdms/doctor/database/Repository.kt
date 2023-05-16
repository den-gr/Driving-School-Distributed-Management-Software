package dsdms.doctor.database

import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots
import java.time.LocalDate


interface Repository {

    /**
     * @param documents: new doctor slot to inserted
     * @return the date for which the new document has been inserted
     */
    fun saveDoctorSlot(documents: DoctorSlot): String

    /**
     * @param data: the date from which we want to obtain occupied doctor slots
     * @return lists (eventually empty) of occupied driving slots in a certain date
     */
    fun getOccupiedDoctorSlots(data: GetBookedDoctorSlots): List<DoctorSlot>

    /**
     * @since each dossier can book only one doctor slot
     * @param dossierId: the id of a dossier
     * @return a repository response status: OK or DELETE_ERROR
     */
    fun deleteDoctorSlot(dossierId: String): RepositoryResponseStatus

    /**
     * @param dossierId: the id of a specific dossier
     * @param today: optional parameters to give the actual date used to take all doctor slots only after today
     * @return a list of doctor slots booked by the dossierId (eventually filtered by date)
     */
    fun getAllDoctorSlots(dossierId: String, today: LocalDate? = null): List<DoctorSlot>
}
