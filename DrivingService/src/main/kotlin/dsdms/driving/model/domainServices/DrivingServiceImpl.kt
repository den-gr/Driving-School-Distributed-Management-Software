package dsdms.driving.model.domainServices

import dsdms.driving.database.Repository
import dsdms.driving.database.mock.ExamService
import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.handlers.getDomainCode
import dsdms.driving.handlers.repositoryToDomainConversionTable
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.GetDrivingSlotDocs
import dsdms.driving.model.valueObjects.licensePlate.LicensePlate
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

data class RegisterDrivingSlotResult(
    val domainResponseStatus: DomainResponseStatus,
    val drivingSlotId: String? = null
)

data class GetDrivingSlotsResult(
    val domainResponseStatus: DomainResponseStatus,
    val drivingSlots: String? = null)

class DrivingServiceImpl(private val repository: Repository) : DrivingService {
    private val examService: ExamService = ExamService()

    override suspend fun saveNewDrivingSlot(documents: DrivingSlotBooking): RegisterDrivingSlotResult {
        val verifyResult = verifyDocuments(documents)
        return if (verifyResult == DomainResponseStatus.OK)
            RegisterDrivingSlotResult(verifyResult, repository.createDrivingSlot(createRegularDrivingSlot(documents)))
        else RegisterDrivingSlotResult(verifyResult)
    }

    private fun createRegularDrivingSlot(documents: DrivingSlotBooking): DrivingSlot {
        return DrivingSlot(
            documents.date.toString(),
            documents.time.toString(),
            documents.instructorId,
            documents.dossierId,
            documents.licensePlate,
            documents.drivingSlotType
        )
    }

    private suspend fun vehicleExist(licensePlate: LicensePlate): Boolean {
        return repository.doesVehicleExist(licensePlate)
    }

    private suspend fun instructorExist(instructorId: String): Boolean {
        return repository.doesInstructorExist(instructorId)
    }

    /**
     * TODO: make a call to future Exam Service to request
     *      info for a possible provisional license for this specific dossier id
     */
    private suspend fun verifyDocuments(drivingSlotBooking: DrivingSlotBooking): DomainResponseStatus {
        val futureDrivingSlots: List<DrivingSlot> = repository.getFutureDrivingSlots()

        val dateAndTime: (DrivingSlot) -> Boolean = { el ->
            el.date == drivingSlotBooking.date.toString() && el.time == drivingSlotBooking.time.toString() }

        val forThisInstructor: (DrivingSlot) -> Boolean = { el ->
            dateAndTime(el) && el.instructorId == drivingSlotBooking.instructorId }

        val forThisVehicle: (DrivingSlot) -> Boolean = { el ->
            dateAndTime(el) && el.licensePlate.toString() == drivingSlotBooking.licensePlate.toString() }

        val forThisDossier: (DrivingSlot) -> Boolean = { el -> el.dossierId == drivingSlotBooking.dossierId }

        val provisionalLicenseResult: RepositoryResponseStatus = examService.getProvisionalLicenseInfo(drivingSlotBooking.dossierId, drivingSlotBooking.date)

        return if (provisionalLicenseResult != RepositoryResponseStatus.OK)
                repositoryToDomainConversionTable.getDomainCode(provisionalLicenseResult)
            else if (vehicleExist(drivingSlotBooking.licensePlate).not() || instructorExist(drivingSlotBooking.instructorId).not())
                DomainResponseStatus.BAD_VEHICLE_INSTRUCTOR_INFO
            else if (futureDrivingSlots.any(forThisDossier))
                DomainResponseStatus.OCCUPIED_DRIVING_SLOTS
            else if (futureDrivingSlots.any(forThisInstructor))
                DomainResponseStatus.INSTRUCTOR_NOT_FREE
            else if (futureDrivingSlots.any(forThisVehicle))
                DomainResponseStatus.VEHICLE_NOT_FREE
            else DomainResponseStatus.OK
    }

    override suspend fun getOccupiedDrivingSlots(docs: GetDrivingSlotDocs): GetDrivingSlotsResult {
        val drivingSlots = repository.getOccupiedDrivingSlots(docs)
        return if (drivingSlots.isEmpty())
            GetDrivingSlotsResult(DomainResponseStatus.NO_SLOT_OCCUPIED)
        else
            GetDrivingSlotsResult(DomainResponseStatus.OK, Json.encodeToString(ListSerializer(DrivingSlot.serializer()), drivingSlots))
    }

    override suspend fun deleteDrivingSlot(drivingSlotId: String): DomainResponseStatus {
        return repositoryToDomainConversionTable.getDomainCode(repository.deleteDrivingSlot(drivingSlotId))
    }
}
