package dsdms.dossier.handlers

import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection


val repositoryToDomainConversionTable: Map<RepositoryResponseStatus, DomainResponseStatus> = mapOf(
    RepositoryResponseStatus.OK to DomainResponseStatus.OK,
    RepositoryResponseStatus.DELETE_ERROR to DomainResponseStatus.DELETE_ERROR,
    RepositoryResponseStatus.UPDATE_ERROR to DomainResponseStatus.UPDATE_ERROR,
)

val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.VALID_DOSSIER_ALREADY_EXISTS to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.ID_NOT_FOUND to HttpURLConnection.HTTP_NOT_FOUND,
    DomainResponseStatus.AGE_NOT_SUFFICIENT to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.NAME_SURNAME_NOT_STRING to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.DELETE_ERROR to HttpURLConnection.HTTP_INTERNAL_ERROR,
    DomainResponseStatus.UPDATE_ERROR to HttpURLConnection.HTTP_CONFLICT,
    DomainResponseStatus.DOSSIER_INVALID to HttpURLConnection.HTTP_ACCEPTED

)

fun getDomainCode(repositoryResponseStatus: RepositoryResponseStatus): DomainResponseStatus {
    return repositoryToDomainConversionTable.getOrDefault(repositoryResponseStatus, DomainResponseStatus.OK)
}

fun getHttpCode(domainResponseStatus: DomainResponseStatus): Int {
    return domainConversionTable.getOrDefault(domainResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}
