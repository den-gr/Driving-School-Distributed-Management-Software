package dsdms.doctor.model.domainServices

import dsdms.doctor.database.Repository
import dsdms.doctor.handlers.getDomainCode
import dsdms.doctor.handlers.repositoryToDomainConversionTable
import dsdms.doctor.model.domainServices.vertxClient.VertxClient
import dsdms.doctor.model.domainServices.vertxClient.VertxClientProvider
import dsdms.doctor.model.entities.DoctorDays
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.entities.DoctorTimeSlot
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots
import kotlinx.datetime.toLocalTime
import java.time.LocalDate

class DoctorServiceImpl(private val repository: Repository) : DoctorService {
    private val dossierServiceConnection: VertxClient = VertxClientProvider()

    override suspend fun verifyDocuments(documents: DoctorSlot): DomainResponseStatus {
        return if (DoctorDays.values().any { el -> el.name == kotlinx.datetime.LocalDate.parse(documents.date).dayOfWeek.name}.not())
            DomainResponseStatus.NOT_DOCTOR_DAY
        else if ((documents.time.toLocalTime() >= DoctorTimeSlot.InitTime.time && documents.time.toLocalTime() <= DoctorTimeSlot.FinishTime.time).not())
            DomainResponseStatus.BAD_TIME
        else if (getOccupiedDoctorSlots(GetBookedDoctorSlots(documents.date)).any { el -> el.time == documents.time })
            DomainResponseStatus.TIME_OCCUPIED
        else if (repository.getAllDoctorSlots(documents.dossierId, LocalDate.now()).isNotEmpty())
            DomainResponseStatus.DOSSIER_ALREADY_BOOKED
        else if (dossierIdExist(documents.dossierId).not())
            DomainResponseStatus.DOSSIER_NOT_EXIST
        else DomainResponseStatus.OK
    }

    private fun dossierIdExist(dossierId: String): Boolean {
//        val response = dossierServiceConnection
//            .getDossierServiceClient()
//            .get("/dossiers/$dossierId")
//            .send()
//
//        return (response.await().statusCode() == HTTP_OK)
        return true
    }

    override suspend fun saveDoctorSlot(documents: DoctorSlot): String {
        return repository.saveDoctorSlot(documents)
    }

    override suspend fun getOccupiedDoctorSlots(data: GetBookedDoctorSlots): List<DoctorSlot> {
        return repository.getOccupiedDoctorSlots(data)
    }

    override suspend fun deleteDoctorSlot(dossierId: String): DomainResponseStatus {
        return repositoryToDomainConversionTable.getDomainCode(repository.deleteDoctorSlot(dossierId))
    }
}
