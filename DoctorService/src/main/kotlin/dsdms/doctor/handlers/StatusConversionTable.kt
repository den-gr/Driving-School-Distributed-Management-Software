@file:Suppress("UnusedReceiverParameter")

package dsdms.doctor.handlers

import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection

val repositoryToDomainConversionTable: Map<RepositoryResponseStatus, DomainResponseStatus> = mapOf(
    RepositoryResponseStatus.OK to DomainResponseStatus.OK,
)

val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.NO_SLOT_OCCUPIED to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.NOT_DOCTOR_DAY to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.BAD_TIME to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.TIME_OCCUPIED to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.DOSSIER_ALREADY_BOOKED to HttpURLConnection.HTTP_BAD_REQUEST
)

fun  Map<DomainResponseStatus, Int>.getHttpCode(domainResponseStatus: DomainResponseStatus): Int {
    return domainConversionTable.getOrDefault(domainResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}

fun Map<RepositoryResponseStatus, DomainResponseStatus>.getDomainCode(repositoryResponseStatus: RepositoryResponseStatus): DomainResponseStatus {
    return repositoryToDomainConversionTable.getOrDefault(repositoryResponseStatus, DomainResponseStatus.OK)
}
