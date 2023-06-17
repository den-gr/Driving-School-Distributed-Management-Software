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

package dsdms.dossier.model.domainServices

import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamEvent
import dsdms.dossier.model.valueObjects.SubscriberDocuments

/**
 * Representing dossier service model implementation.
 */
interface DossierDomainService {

    /**
     * @see SubscriberDocuments
     * @param givenDocuments: to create and then save a new dossier referred to a specific subscriber
     * @since the insert could be faulty, the ID could be null
     * @return the dossier id and DomainResponseStatus:
     *  - OK
     *  - AGE_NOT_SUFFICIENT --> subscriber does not meet minimum 16 age
     *  - VALID_DOSSIER_ALREADY_EXIST --> for this subscriber (check by fiscal code)
     *  - NAME_SURNAME_NOT_STRING --> given data is faulty
     */
    suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): SaveDossierResult

    /**
     * @see Dossier
     * @param id of a specific dossier
     * @return Dossier (could be null if id does not exist)
     * - DOSSIER_INVALID
     * - OK
     */
    suspend fun readDossierFromId(id: String): GetDossierResult

    /**
     * @param event that notify what happens
     * @return RepositoryResponseStatus:
     * - UPDATE_ERROR --> some error occurred (result was not acknowledge)
     * - OK otherwise
     */
    suspend fun updateExamStatus(event: ExamEvent, id: String): DomainResponseStatus
}
