package dsdms.doctor.model.domainServices

import dsdms.doctor.database.Repository
import dsdms.doctor.handlers.getDomainCode
import dsdms.doctor.handlers.repositoryToDomainConversionTable
import dsdms.doctor.model.entities.DoctorDays
import dsdms.doctor.model.entities.DoctorSlot
import dsdms.doctor.model.entities.DoctorTimeSlot
import dsdms.doctor.model.valueObjects.DoctorResult
import dsdms.doctor.model.valueObjects.GetBookedDoctorSlots
import dsdms.doctor.model.valueObjects.ResultTypes
import dsdms.exam.model.valueObjects.ExamPassData
import io.vertx.core.buffer.Buffer
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import kotlinx.datetime.toLocalTime
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection.HTTP_OK
import java.time.LocalDate

data class InsertDoctorVisitResult(
    val domainResponseStatus: DomainResponseStatus,
    val visitDate: String? = null
)

data class BookedDoctorSlots(
    val domainResponseStatus: DomainResponseStatus,
    val doctorSlots: String? = null
)

class DoctorServiceImpl(private val repository: Repository, private val dossierServiceConnection: WebClient, private val examServiceConnection: WebClient) : DoctorService {

    private suspend fun verifyDocuments(documents: DoctorSlot): DomainResponseStatus {
        return if (checkDoctorDay(documents.date))
            DomainResponseStatus.NOT_DOCTOR_DAY
        else if (checkDoctorVisitGivenTime(documents.time))
            DomainResponseStatus.BAD_TIME
        else if (checkTimeAvailability(documents.time, documents.date))
            DomainResponseStatus.TIME_OCCUPIED
        else if (repository.getAllDoctorSlots(documents.dossierId, LocalDate.now()).isNotEmpty())
            DomainResponseStatus.DOSSIER_ALREADY_BOOKED
        else if (dossierIdExist(documents.dossierId).not())
            DomainResponseStatus.DOSSIER_NOT_EXIST
        else DomainResponseStatus.OK
    }

    /**
     * @param date: the given date from doctor slot documents
     * @return if wanted date is a Doctor Day (Tuesday or Friday)
     */
    private fun checkDoctorDay(date: String): Boolean =
        DoctorDays.values().any { el -> el.name == LocalDate.parse(date).dayOfWeek.name}.not()

    /**
     * @param time: wanted time of the visit
     * @return if given time per the doctor visit is in the correct time slot and is available
     */
    private fun checkDoctorVisitGivenTime(time: String): Boolean =
        (time.toLocalTime() >= DoctorTimeSlot.InitTime.time && time.toLocalTime() <= DoctorTimeSlot.FinishTime.time).not()

    /**
     * @param time: wanted time of the visit
     * @param date: wanted date of the visit
     * @return if given time is available
     */
    private suspend fun checkTimeAvailability(time: String, date: String): Boolean {
        val result = getOccupiedDoctorSlots(GetBookedDoctorSlots(date)).doctorSlots
        return if (result != null)
            Json.decodeFromString(ListSerializer(DoctorSlot.serializer()), result)
                .any { el -> el.time == time }
        else false
    }

    private suspend fun dossierIdExist(dossierId: String): Boolean {
        return dossierServiceConnection
            .get("/dossiers/$dossierId")
            .send()
            .await()
            .statusCode() == HTTP_OK
    }

    override suspend fun saveDoctorSlot(documents: DoctorSlot): InsertDoctorVisitResult {
        val verifyResult = verifyDocuments(documents)
        return if (verifyResult == DomainResponseStatus.OK)
            InsertDoctorVisitResult(verifyResult, repository.saveDoctorSlot(documents))
        else InsertDoctorVisitResult(verifyResult)
    }

    override suspend fun getOccupiedDoctorSlots(data: GetBookedDoctorSlots): BookedDoctorSlots {
        val doctorSlots = repository.getOccupiedDoctorSlots(data)
        return if (doctorSlots.isEmpty())
            BookedDoctorSlots(DomainResponseStatus.NO_SLOT_OCCUPIED)
        else BookedDoctorSlots(
            DomainResponseStatus.OK,
            Json.encodeToString(ListSerializer(DoctorSlot.serializer()), doctorSlots)
        )
    }

    override suspend fun deleteDoctorSlot(dossierId: String): DomainResponseStatus {
        return repositoryToDomainConversionTable.getDomainCode(repository.deleteDoctorSlot(dossierId))
    }

    override suspend fun saveDoctorResult(document: DoctorResult): DomainResponseStatus {
        return if (document.result != ResultTypes.VALID.toString())
                DomainResponseStatus.EXAM_PASS_NOT_CREATED
            else if (createTheoreticalExamPass(document))
                repositoryToDomainConversionTable.getDomainCode(repository.registerDoctorResult(document))
            else
                DomainResponseStatus.EXAM_PASS_ALREADY_AVAILABLE
    }

    private suspend fun createTheoreticalExamPass(document: DoctorResult): Boolean {
        return examServiceConnection
            .put("/theoreticalExam/pass")
            .sendBuffer(createJson(ExamPassData(document.dossierId, document.date)))
            .await()
            .statusCode() == HTTP_OK
    }

    private inline fun <reified T> createJson(docs: T): Buffer? {
        return Buffer.buffer(Json.encodeToString(docs))
    }
}
