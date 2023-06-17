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

package dsdms.driving.handlers

import dsdms.driving.database.utils.RepositoryResponseStatus
import dsdms.driving.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection

/**
 * Convert RepositoryResponseStatus to corresponding DomainResponseStatus.
 */
val repositoryToDomainConversionTable: Map<RepositoryResponseStatus, DomainResponseStatus> = mapOf(
    RepositoryResponseStatus.OK to DomainResponseStatus.OK,
    RepositoryResponseStatus.DELETE_ERROR to DomainResponseStatus.DELETE_ERROR,
)

/**
 * Convert DomainResponseStatus to corresponding HTTP code.
 */
val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.INSTRUCTOR_NOT_FREE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.VEHICLE_NOT_FREE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.OCCUPIED_DRIVING_SLOTS to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.INVALID_PROVISIONAL_LICENSE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.NO_PROVISIONAL_LICENSE to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.DELETE_ERROR to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.BAD_VEHICLE_INSTRUCTOR_INFO to HttpURLConnection.HTTP_NOT_FOUND,
    DomainResponseStatus.ALREADY_DEFINED_AS_PRACTICAL_EXAM_DAY to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.NOT_ENOUGH_DRIVING_LESSONS_FOR_EXAM to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.NOT_AN_EXAM_DAY to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.NO_SLOT_OCCUPIED to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.PROVISIONAL_LICENSE_NOT_VALID to HttpURLConnection.HTTP_BAD_REQUEST,
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
