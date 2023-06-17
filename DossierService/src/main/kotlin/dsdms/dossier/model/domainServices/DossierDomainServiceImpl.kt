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

import dsdms.dossier.database.Repository
import dsdms.dossier.handlers.getDomainCode
import dsdms.dossier.model.domainServices.subscriberCheck.SubscriberControls
import dsdms.dossier.model.domainServices.subscriberCheck.SubscriberControlsImpl
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamEvent
import dsdms.dossier.model.valueObjects.ExamsStatus
import dsdms.dossier.model.valueObjects.PracticalExamState
import dsdms.dossier.model.valueObjects.SubscriberDocuments

/**
 * @param domainResponseStatus of saving dossier operation
 * @param dossierId if operation was successful, otherwise null
 */
data class SaveDossierResult(
    val domainResponseStatus: DomainResponseStatus,
    val dossierId: String? = null,
)

/**
 * @param domainResponseStatus of requesting dossier operation
 * @param dossier if operation was successful, otherwise null
 */
data class GetDossierResult(
    val domainResponseStatus: DomainResponseStatus,
    val dossier: Dossier? = null,
)

/**
 * @param repository of dossier data storage
 */
class DossierDomainServiceImpl(private val repository: Repository) : DossierDomainService {
    private val subscriberControls: SubscriberControls = SubscriberControlsImpl()

    override suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): SaveDossierResult {
        val verifyResult = verifyDocuments(givenDocuments)
        return if (verifyResult == DomainResponseStatus.OK) {
            SaveDossierResult(verifyResult, createDossier(givenDocuments))
        } else {
            return SaveDossierResult(verifyResult)
        }
    }

    private suspend fun createDossier(givenDocuments: SubscriberDocuments): String? =
        repository.createDossier(
            Dossier(
                givenDocuments.name,
                givenDocuments.surname,
                givenDocuments.birthdate.toString(),
                givenDocuments.fiscal_code,
            ),
        )

    private suspend fun verifyDocuments(documents: SubscriberDocuments): DomainResponseStatus {
        return if (subscriberControls.checkDuplicatedFiscalCode(documents, repository)) {
            DomainResponseStatus.VALID_DOSSIER_ALREADY_EXISTS
        } else if (subscriberControls.checkSubscriberBirthdate(documents)) {
            DomainResponseStatus.AGE_NOT_SUFFICIENT
        } else if (subscriberControls.isNumeric(documents.name, documents.surname)) {
            DomainResponseStatus.NAME_SURNAME_NOT_STRING
        } else {
            DomainResponseStatus.OK
        }
    }

    override suspend fun readDossierFromId(id: String): GetDossierResult {
        val dossier: Dossier? = repository.readDossierFromId(id)
        return if (dossier == null) {
            GetDossierResult(DomainResponseStatus.ID_NOT_FOUND)
        } else if (dossier.validity.not()) {
            GetDossierResult(DomainResponseStatus.DOSSIER_INVALID, dossier)
        } else {
            GetDossierResult(DomainResponseStatus.OK, dossier)
        }
    }

    override suspend fun updateExamStatus(event: ExamEvent, id: String): DomainResponseStatus {
        val dossier = readDossierFromId(id).dossier
        if (dossier != null) {
            return try {
                val newExamState = getNewExamProgressState(event, dossier.examsStatus)
                if (newExamState.practicalExamState == PracticalExamState.SECOND_PROVISIONAL_LICENCE_INVALID) {
                    getDomainCode(repository.updateDossier(dossier.copy(examsStatus = newExamState, validity = false)))
                } else {
                    getDomainCode(repository.updateDossier(dossier.copy(examsStatus = newExamState)))
                }
            } catch (ex: IllegalStateException) {
                println(ex.message)
                DomainResponseStatus.UPDATE_ERROR
            }
        }
        return DomainResponseStatus.ID_NOT_FOUND
    }

    private fun getNewExamProgressState(event: ExamEvent, currentExamsStatus: ExamsStatus): ExamsStatus {
        return when (event) {
            ExamEvent.THEORETICAL_EXAM_PASSED -> currentExamsStatus.registerTheoreticalExamPassed()
            ExamEvent.PRACTICAL_EXAM_PASSED -> currentExamsStatus.registerPracticalExamPassed()
            ExamEvent.PROVISIONAL_LICENSE_INVALIDATION ->
                currentExamsStatus.registerProvisionalLicenceInvalidation()
        }
    }
}
