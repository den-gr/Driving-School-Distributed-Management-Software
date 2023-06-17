/*******************************************************************************
 * MIT License
 *
 * Copyright 2023 DSDMS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE
 ******************************************************************************/

@file:Suppress("UnusedReceiverParameter")

package dsdms.doctor.handlers

import dsdms.doctor.database.utils.RepositoryResponseStatus
import dsdms.doctor.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection

/**
 * Convert RepositoryResponseStatus to corresponding DomainResponseStatus.
 */
val repositoryToDomainConversionTable: Map<RepositoryResponseStatus, DomainResponseStatus> = mapOf(
    RepositoryResponseStatus.OK to DomainResponseStatus.OK,
    RepositoryResponseStatus.INSERT_ERROR to DomainResponseStatus.INSERT_ERROR,
    RepositoryResponseStatus.DELETE_ERROR to DomainResponseStatus.DELETE_ERROR,
)

/**
 * Convert DomainResponseStatus to corresponding HTTP code.
 */
val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.NO_SLOT_OCCUPIED to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.NOT_DOCTOR_DAY to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.BAD_TIME to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.TIME_OCCUPIED to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.DOSSIER_ALREADY_BOOKED to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.DOSSIER_NOT_EXIST to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.INSERT_ERROR to HttpURLConnection.HTTP_INTERNAL_ERROR,
    DomainResponseStatus.EXAM_PASS_ALREADY_AVAILABLE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.EXAM_PASS_NOT_CREATED to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.DELETE_ERROR to HttpURLConnection.HTTP_INTERNAL_ERROR,
)

/**
 * @param domainResponseStatus that must be converted in HTTP code
 * @return value from domainConversionTable or HTTP_INTERNAL_ERROR if it not finds a value in the conversion table
 */
fun Map<DomainResponseStatus, Int>.getHttpCode(domainResponseStatus: DomainResponseStatus): Int {
    return domainConversionTable.getOrDefault(domainResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}

/**
 * @param repositoryResponseStatus that must be converted in DomainResponseStatus
 * @return value from repositoryToDomainConversionTable or OK if it not finds a value in the conversion table
 */
fun Map<RepositoryResponseStatus, DomainResponseStatus>
    .getDomainCode(repositoryResponseStatus: RepositoryResponseStatus): DomainResponseStatus {
    return repositoryToDomainConversionTable.getOrDefault(repositoryResponseStatus, DomainResponseStatus.OK)
}
