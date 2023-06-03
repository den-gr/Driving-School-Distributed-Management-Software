package dsdms.driving.model.domainServices

import dsdms.driving.channels.ChannelsProvider
import dsdms.driving.database.Repository
import dsdms.driving.handlers.getDomainCode
import dsdms.driving.handlers.repositoryToDomainConversionTable
import dsdms.driving.model.entities.DrivingSlot
import dsdms.driving.model.valueObjects.DrivingSlotBooking
import dsdms.driving.model.valueObjects.DrivingSlotType
import dsdms.driving.model.valueObjects.DrivingSlotsRequest
import dsdms.driving.model.valueObjects.LicensePlate
import dsdms.driving.model.valueObjects.PracticalExamDay
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class DrivingServiceImpl(private val repository: Repository, private val channels: ChannelsProvider) : DrivingService {
    companion object {
        private const val MIN_NUMBER_OF_DRIVING_LESSONS: Int = 10
    }

    override suspend fun saveNewDrivingSlot(documents: DrivingSlotBooking): DrivingSlotRegistrationResult {
        val verifyResult = verifyDocuments(documents)
        if (verifyResult == DomainResponseStatus.OK) {
            return if (documents.drivingSlotType == DrivingSlotType.EXAM) {
               saveNewExamDrivingSlot(documents)
            } else {
                DrivingSlotRegistrationResult(
                    verifyResult,
                    repository.createDrivingSlot(createRegularDrivingSlot(documents)))
            }
        }
        return DrivingSlotRegistrationResult(verifyResult)
    }

    private suspend fun saveNewExamDrivingSlot(documents: DrivingSlotBooking): DrivingSlotRegistrationResult {
        val numCompletedDrivingLessons = repository.countPastDrivingSlots(documents.dossierId)
        return if (numCompletedDrivingLessons < MIN_NUMBER_OF_DRIVING_LESSONS) {
            DrivingSlotRegistrationResult(DomainResponseStatus.NOT_ENOUGH_DRIVING_LESSONS_FOR_EXAM)
        } else if (!repository.getPracticalExamDays().toList().map(PracticalExamDay::date).contains(documents.date)) {
           DrivingSlotRegistrationResult(DomainResponseStatus.NOT_AN_EXAM_DAY)
        } else {
            DrivingSlotRegistrationResult(
                DomainResponseStatus.OK,
                repository.createDrivingSlot(createRegularDrivingSlot(documents)))
        }
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
            el.date == drivingSlotBooking.date.toString() && el.time == drivingSlotBooking.time.toString()
        }

        val forThisInstructor: (DrivingSlot) -> Boolean = { el ->
            dateAndTime(el) && el.instructorId == drivingSlotBooking.instructorId
        }

        val forThisVehicle: (DrivingSlot) -> Boolean = { el ->
            dateAndTime(el) && el.licensePlate.toString() == drivingSlotBooking.licensePlate.toString()
        }

        val forThisDossier: (DrivingSlot) -> Boolean = { el -> el.dossierId == drivingSlotBooking.dossierId }

        val provisionalLicenseResult: DomainResponseStatus
        = channels.examServiceChannel.isProvisionalLicenseValid(drivingSlotBooking.dossierId, drivingSlotBooking.date)

        return if (provisionalLicenseResult != DomainResponseStatus.OK) {
            provisionalLicenseResult
        } else if (vehicleExist(drivingSlotBooking.licensePlate).not()
                || instructorExist(drivingSlotBooking.instructorId).not()) {
            DomainResponseStatus.BAD_VEHICLE_INSTRUCTOR_INFO
        } else if (futureDrivingSlots.any(forThisDossier)) {
            DomainResponseStatus.OCCUPIED_DRIVING_SLOTS
        } else if (futureDrivingSlots.any(forThisInstructor)) {
            DomainResponseStatus.INSTRUCTOR_NOT_FREE
        } else if (futureDrivingSlots.any(forThisVehicle)) {
            DomainResponseStatus.VEHICLE_NOT_FREE
        } else {
            DomainResponseStatus.OK
        }
    }

    override suspend fun getOccupiedDrivingSlots(docs: DrivingSlotsRequest): DrivingSlotsRequestResult {
        val drivingSlots = repository.getOccupiedDrivingSlots(docs)
        return if (drivingSlots.isEmpty()) {
            DrivingSlotsRequestResult(DomainResponseStatus.NO_SLOT_OCCUPIED)
        } else {
            DrivingSlotsRequestResult(
                DomainResponseStatus.OK,
                Json.encodeToString(ListSerializer(DrivingSlot.serializer()), drivingSlots))
        }
    }

    override suspend fun deleteDrivingSlot(drivingSlotId: String): DomainResponseStatus {
        return repositoryToDomainConversionTable.getDomainCode(repository.deleteDrivingSlot(drivingSlotId))
    }
}
