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

import dsdms.dossier.model.domainServices.subscriberCheck.SubscriberControls

/**
 * Types of responses of dossier context.
 */
enum class DomainResponseStatus {
    /**
     * Otherwise...
     */
    OK,

    /**
     * While registering a new dossier, one is already present for a specific Fiscal code.
     * @see DossierDomainService
     */
    VALID_DOSSIER_ALREADY_EXISTS,

    /**
     * Given id to read a Dossier does not exist.
     * @see DossierDomainService
     */
    ID_NOT_FOUND,

    /**
     * Subscriber does not meet minimum age requirement to be registered.
     * @see SubscriberControls
     */
    AGE_NOT_SUFFICIENT,

    /**
     * Given info about a subscriber are not the correct format.
     * @see SubscriberControls
     */
    NAME_SURNAME_NOT_STRING,

    /**
     * Used when subscriber has already reached limit number of practical exam attempts (3).
     */
    MAX_ATTEMPTS_REACHED,

    /**
     * If there was an error during delete operation.
     */
    DELETE_ERROR,

    /**
     * Update operation was failed.
     */
    UPDATE_ERROR,

    /**
     * Dossier is not valid.
     */
    DOSSIER_INVALID,
}
