package dsdms.driving.handlers

import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection

val dbConversionTable: Map<RepositoryResponseStatus, Int> = mapOf(
    RepositoryResponseStatus.OK to HttpURLConnection.HTTP_OK,
    RepositoryResponseStatus.DELETE_ERROR to HttpURLConnection.HTTP_NOT_MODIFIED,
    RepositoryResponseStatus.UPDATE_ERROR to HttpURLConnection.HTTP_NOT_MODIFIED,
    RepositoryResponseStatus.MULTIPLE_EQUAL_IDS to HttpURLConnection.HTTP_MULT_CHOICE
)

val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.NO_SLOT_OCCUPIED to HttpURLConnection.HTTP_OK,
    )

fun Map<RepositoryResponseStatus, Int>.getHttpCode(repositoryResponseStatus: RepositoryResponseStatus): Int {
    return dbConversionTable.getOrDefault(repositoryResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}

fun Map<DomainResponseStatus, Int>.getHttpCode(domainResponseStatus: DomainResponseStatus): Int {
    return domainConversionTable.getOrDefault(domainResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}