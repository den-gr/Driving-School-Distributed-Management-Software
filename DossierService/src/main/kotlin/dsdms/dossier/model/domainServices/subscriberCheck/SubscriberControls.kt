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
import dsdms.dossier.model.valueObjects.SubscriberDocuments

/**
 * Some dossier constants.
 */
object SubscriberConstants {
    /**
     * Min age for a subscriber to be subscribed.
     */
    const val MIN_AGE: Int = 16
}

/**
 * Methods for checking constrains of subscriber data.
 */
interface SubscriberControls {
    /**
     * @see SubscriberDocuments
     * @see Repository
     * @param givenDocuments for a specific subscriber
     * @param repository to check if info exists into db
     * @return True if a dossier already exists for a specific fiscal code, false otherwise
     */
    suspend fun checkDuplicatedFiscalCode(givenDocuments: SubscriberDocuments, repository: Repository): Boolean

    /**
     * @param givenDocuments
     * @return true if subscriber does not meet minimum age requirement, false otherwise
     */
    suspend fun checkSubscriberBirthdate(givenDocuments: SubscriberDocuments): Boolean

    /**
     * @param string: one or multiple string to check if they are in reality numeric
     * @return true if even just one string is numeric, false otherwise
     */
    suspend fun isNumeric(vararg string: String): Boolean
}
