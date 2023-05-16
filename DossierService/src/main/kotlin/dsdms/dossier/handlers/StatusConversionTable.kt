package dsdms.dossier.handlers

import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection


val dbConversionTable: Map<RepositoryResponseStatus, Int> = mapOf(
    RepositoryResponseStatus.OK to HttpURLConnection.HTTP_OK,
    RepositoryResponseStatus.DELETE_ERROR to HttpURLConnection.HTTP_INTERNAL_ERROR,
    RepositoryResponseStatus.UPDATE_ERROR to HttpURLConnection.HTTP_INTERNAL_ERROR,
)

val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.FISCAL_CODE_DUPLICATION to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.ID_NOT_FOUND to HttpURLConnection.HTTP_NOT_FOUND,
    DomainResponseStatus.AGE_NOT_SUFFICIENT to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.NAME_SURNAME_NOT_STRING to HttpURLConnection.HTTP_BAD_REQUEST

)

fun Map<RepositoryResponseStatus, Int>.getHttpCode(repositoryResponseStatus: RepositoryResponseStatus): Int {
    return dbConversionTable.getOrDefault(repositoryResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}

fun Map<DomainResponseStatus, Int>.getHttpCode(domainResponseStatus: DomainResponseStatus): Int {
    return domainConversionTable.getOrDefault(domainResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}
