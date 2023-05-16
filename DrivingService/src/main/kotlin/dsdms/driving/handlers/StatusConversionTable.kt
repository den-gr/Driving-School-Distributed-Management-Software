@file:Suppress("UnusedReceiverParameter")

package dsdms.driving.handlers

import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection

val repositoryToDomainConversionTable: Map<RepositoryResponseStatus, DomainResponseStatus> = mapOf(
    RepositoryResponseStatus.OK to DomainResponseStatus.OK,
    RepositoryResponseStatus.DELETE_ERROR to DomainResponseStatus.DELETE_ERROR,
    RepositoryResponseStatus.NO_PROVISIONAL_LICENSE to DomainResponseStatus.NO_PROVISIONAL_LICENSE,
    RepositoryResponseStatus.INVALID_PROVISIONAL_LICENSE to DomainResponseStatus.INVALID_PROVISIONAL_LICENSE
)

val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.NO_SLOT_OCCUPIED to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.INSTRUCTOR_NOT_FREE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.VEHICLE_NOT_FREE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.OCCUPIED_DRIVING_SLOTS to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.INVALID_PROVISIONAL_LICENSE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.NO_PROVISIONAL_LICENSE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.DELETE_ERROR to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.BAD_VEHICLE_INSTRUCTOR_INFO to HttpURLConnection.HTTP_NOT_FOUND,
)

fun  Map<DomainResponseStatus, Int>.getHttpCode(domainResponseStatus: DomainResponseStatus): Int {
    return domainConversionTable.getOrDefault(domainResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}

fun Map<RepositoryResponseStatus, DomainResponseStatus>.getDomainCode(repositoryResponseStatus: RepositoryResponseStatus): DomainResponseStatus {
    return repositoryToDomainConversionTable.getOrDefault(repositoryResponseStatus, DomainResponseStatus.OK)
}