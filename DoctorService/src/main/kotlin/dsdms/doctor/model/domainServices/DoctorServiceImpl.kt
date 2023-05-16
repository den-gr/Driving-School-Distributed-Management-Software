package dsdms.doctor.model.domainServices

import dsdms.doctor.database.Repository
import dsdms.doctor.handlers.getDomainCode
import dsdms.doctor.handlers.repositoryToDomainConversionTable
import dsdms.doctor.model.entities.DoctorDays
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.entities.DoctorTimeSlot
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalTime

class DoctorServiceImpl(private val repository: Repository) : DoctorService {

    override fun verifyDocuments(documents: DoctorSlot): DomainResponseStatus {
        return if (DoctorDays.values().any { el -> el.name == LocalDate.parse(documents.date).dayOfWeek.name}.not())
            DomainResponseStatus.NOT_DOCTOR_DAY
        else if ((documents.time.toLocalTime() > DoctorTimeSlot.InitTime.time && documents.time.toLocalTime() < DoctorTimeSlot.FinishTime.time).not())
            DomainResponseStatus.BAD_TIME
        else if (getOccupiedDoctorSlots(GetBookedDoctorSlots(documents.date)).any { el -> el.time == documents.time })
            DomainResponseStatus.TIME_OCCUPIED
        else if (getOccupiedDoctorSlots(GetBookedDoctorSlots(documents.date)).any { el -> el.dossierId == documents.dossierId })
            DomainResponseStatus.DOSSIER_ALREADY_BOOKED
        else DomainResponseStatus.OK
    }

    override fun saveDoctorSlot(documents: DoctorSlot): String {
        return repository.saveDoctorSlot(documents)
    }

    override fun getOccupiedDoctorSlots(data: GetBookedDoctorSlots): List<DoctorSlot> {
        return repository.getOccupiedDoctorSlots(data)
    }

    override fun deleteDoctorSlot(dossierId: String): DomainResponseStatus {
        return repositoryToDomainConversionTable.getDomainCode(repository.deleteDoctorSlot(dossierId))
    }
}
