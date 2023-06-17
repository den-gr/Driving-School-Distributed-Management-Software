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

package dsdms.exam.model.domainServices

import dsdms.exam.channels.ChannelsProvider
import dsdms.exam.database.Repository
import dsdms.exam.handlers.getDomainCode
import dsdms.exam.handlers.repositoryToDomainConversionTable
import dsdms.exam.model.entities.ProvisionalLicense
import dsdms.exam.model.valueObjects.ExamEvent
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder
import kotlinx.datetime.LocalDate

/**
 * Implements provisional license logics.
 */
class ProvisionalLicenseDomainServiceImpl(
    private val repository: Repository,
    private val channelsProvider: ChannelsProvider,
) : ProvisionalLicenseDomainService {
    override suspend fun registerProvisionalLicense(provisionalLicense: ProvisionalLicense): DomainResponseStatus {
        if (areThereAnotherProvisionalLicense(provisionalLicense.dossierId)) {
            return DomainResponseStatus.PROVISIONAL_LICENSE_ALREADY_EXISTS
        }
        val eventNotificationResult = channelsProvider.dossierServiceChannel
            .updateExamStatus(provisionalLicense.dossierId, ExamEvent.THEORETICAL_EXAM_PASSED)

        return if (eventNotificationResult != DomainResponseStatus.OK) {
            eventNotificationResult
        } else {
            repositoryToDomainConversionTable.getDomainCode(
                repository.saveProvisionalLicenseHolder(ProvisionalLicenseHolder(provisionalLicense)),
            )
        }
    }

    override suspend fun getProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder? {
        return repository.findProvisionalLicenseHolder(dossierId)
    }

    override suspend fun isProvisionalLicenseValid(dossierId: String, date: LocalDate): DomainResponseStatus {
        val provisionalLicenseHolder = getProvisionalLicenseHolder(dossierId)
            ?: return DomainResponseStatus.ID_NOT_FOUND
        return if (provisionalLicenseHolder.isValidOn(date)) {
            DomainResponseStatus.OK
        } else {
            DomainResponseStatus.PROVISIONAL_LICENSE_NOT_VALID
        }
    }

    override suspend fun incrementProvisionalLicenseFailures(dossierId: String): DomainResponseStatus {
        val provisionalLicenseHolder = getProvisionalLicenseHolder(dossierId)
            ?: return DomainResponseStatus.ID_NOT_FOUND
        val holder = provisionalLicenseHolder.registerPracticalExamFailure()
        return if (holder.hasMaxAttempts()) {
            deleteProvisionalLicenseWithInvalidation(dossierId)
        } else {
            repositoryToDomainConversionTable.getDomainCode(repository.updateProvisionalLicenseHolder(holder))
        }
    }

    private suspend fun deleteProvisionalLicenseWithInvalidation(dossierId: String): DomainResponseStatus {
        val status = repositoryToDomainConversionTable
            .getDomainCode(repository.deleteProvisionalLicenseHolder(dossierId))
        return if (status == DomainResponseStatus.OK) {
            channelsProvider.dossierServiceChannel
                .updateExamStatus(dossierId, ExamEvent.PROVISIONAL_LICENSE_INVALIDATION)
        } else {
            status
        }
    }

    override suspend fun practicalExamSuccess(dossierId: String): DomainResponseStatus {
        val isDossierValidResult = channelsProvider.dossierServiceChannel.checkDossierValidity(dossierId)
        if (isDossierValidResult == DomainResponseStatus.OK) {
            val updateExamStateResult = channelsProvider.dossierServiceChannel
                .updateExamStatus(dossierId, ExamEvent.PRACTICAL_EXAM_PASSED)
            return if (updateExamStateResult == DomainResponseStatus.OK) {
                repositoryToDomainConversionTable.getDomainCode(repository.deleteProvisionalLicenseHolder(dossierId))
            } else {
                updateExamStateResult
            }
        }
        return isDossierValidResult
    }

    private suspend fun areThereAnotherProvisionalLicense(dossierId: String): Boolean {
        return repository.findProvisionalLicenseHolder(dossierId) != null
    }
}
