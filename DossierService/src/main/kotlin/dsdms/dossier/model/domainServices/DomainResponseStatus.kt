package dsdms.dossier.model.domainServices

enum class DomainResponseStatus {
    OK,
    VALID_DOSSIER_ALREADY_EXISTS,
    ID_NOT_FOUND,
    AGE_NOT_SUFFICIENT,
    NAME_SURNAME_NOT_STRING
}
