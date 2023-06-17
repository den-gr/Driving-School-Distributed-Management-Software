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

package dsdms.dossier.handlers

import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.domainServices.DomainResponseStatus
import java.net.HttpURLConnection

/**
 * Convert RepositoryResponseStatus to corresponding DomainResponseStatus.
 */
val repositoryToDomainConversionTable: Map<RepositoryResponseStatus, DomainResponseStatus> = mapOf(
    RepositoryResponseStatus.OK to DomainResponseStatus.OK,
    RepositoryResponseStatus.DELETE_ERROR to DomainResponseStatus.DELETE_ERROR,
    RepositoryResponseStatus.UPDATE_ERROR to DomainResponseStatus.UPDATE_ERROR,
)

/**
 * Convert DomainResponseStatus to corresponding HTTP code.
 */
val domainConversionTable: Map<DomainResponseStatus, Int> = mapOf(
    DomainResponseStatus.OK to HttpURLConnection.HTTP_OK,
    DomainResponseStatus.VALID_DOSSIER_ALREADY_EXISTS to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.ID_NOT_FOUND to HttpURLConnection.HTTP_NOT_FOUND,
    DomainResponseStatus.AGE_NOT_SUFFICIENT to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.NAME_SURNAME_NOT_STRING to HttpURLConnection.HTTP_BAD_REQUEST,
    DomainResponseStatus.DELETE_ERROR to HttpURLConnection.HTTP_INTERNAL_ERROR,
    DomainResponseStatus.UPDATE_ERROR to HttpURLConnection.HTTP_CONFLICT,
    DomainResponseStatus.DOSSIER_INVALID to HttpURLConnection.HTTP_ACCEPTED,

)

/**
 * @param repositoryResponseStatus that must be converted in DomainResponseStatus
 * @return value from repositoryToDomainConversionTable or OK if it not finds a value in the conversion table
 */
fun getDomainCode(repositoryResponseStatus: RepositoryResponseStatus): DomainResponseStatus {
    return repositoryToDomainConversionTable.getOrDefault(repositoryResponseStatus, DomainResponseStatus.OK)
}

/**
 * @param domainResponseStatus that must be converted in HTTP code
 * @return value from domainConversionTable or HTTP_INTERNAL_ERROR if it not finds a value in the conversion table
 */
fun getHttpCode(domainResponseStatus: DomainResponseStatus): Int {
    return domainConversionTable.getOrDefault(domainResponseStatus, HttpURLConnection.HTTP_INTERNAL_ERROR)
}
