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

package dsdms.dossier.model.domainServices.subscriberCheck

import dsdms.dossier.database.Repository
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.SubscriberDocuments
import kotlinx.datetime.toJavaLocalDate
import java.time.Period

/**
 * Allows to verify correctness of subscriber date.
 */
class SubscriberControlsImpl : SubscriberControls {
    override suspend fun checkDuplicatedFiscalCode(
        givenDocuments: SubscriberDocuments,
        repository: Repository,
    ): Boolean {
        val alreadyExistingDossiers: List<Dossier> = repository.readDossierFromCf(givenDocuments.fiscal_code)
        return alreadyExistingDossiers.count { el -> el.validity } != 0
    }

    override suspend fun checkSubscriberBirthdate(givenDocuments: SubscriberDocuments): Boolean {
        return Period.between(
            givenDocuments.birthdate.toJavaLocalDate(),
            java.time.LocalDate.now(),
        ).years < SubscriberConstants.MIN_AGE
    }

    /**
     * At least one given string matches the regex, it means that is a number, not a real string.
     */
    override suspend fun isNumeric(vararg string: String): Boolean {
        return string.any { el -> el.matches(Regex(".*[0-9].*")) }
    }
}
