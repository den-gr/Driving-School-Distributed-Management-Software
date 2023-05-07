package dsdms.dossier.handlers

import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection

val dbConversionTable: Map<RepositoryResponseStatus, Int> = mapOf(
    RepositoryResponseStatus.OK to HttpURLConnection.HTTP_OK,
    RepositoryResponseStatus.DELETE_ERROR to HttpURLConnection.HTTP_NOT_MODIFIED,
    RepositoryResponseStatus.UPDATE_ERROR to HttpURLConnection.HTTP_NOT_MODIFIED,
    RepositoryResponseStatus.MULTIPLE_EQUAL_IDS to HttpURLConnection.HTTP_MULT_CHOICE
)

val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.FISCAL_CODE_DUPLICATION to HttpURLConnection.HTTP_CONFLICT,
    DomainResponseStatus.ID_NOT_FOUND to HttpURLConnection.HTTP_NOT_FOUND

)

fun Map<RepositoryResponseStatus, Int>.getHttpCode(repositoryResponseStatus: RepositoryResponseStatus): Int {
    return dbConversionTable.getOrDefault(repositoryResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}

fun Map<DomainResponseStatus, Int>.getHttpCode(domainResponseStatus: DomainResponseStatus): Int {
    return domainConversionTable.getOrDefault(domainResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}
