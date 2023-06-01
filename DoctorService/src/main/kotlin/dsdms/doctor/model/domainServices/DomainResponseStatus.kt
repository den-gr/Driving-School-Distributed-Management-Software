package dsdms.doctor.model.domainServices

enum class DomainResponseStatus {
    OK,
    NO_SLOT_OCCUPIED,
    NOT_DOCTOR_DAY,
    BAD_TIME,
    TIME_OCCUPIED,
    DOSSIER_ALREADY_BOOKED,
    DOSSIER_NOT_EXIST,
    INSERT_ERROR,
    EXAM_PASS_ALREADY_AVAILABLE,
    EXAM_PASS_NOT_CREATED,
    DELETE_ERROR,
    DOSSIER_NOT_VALID
}
