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

import dsdms.dossier.model.domainServices.DossierDomainService
import io.vertx.ext.web.RoutingContext

/**
 * Server routes handlers.
 */
interface RouteHandlers {

    /**
     * Handles new dossier registration.
     * @param routingContext
     * @see DossierDomainService
     * @return:
     *  - code 400 for bad request is something happen during decoding of json body
     *  - code 200 with id of created dossier
     *  - code 500 in cas of internal server errors
     */
    suspend fun handleDossierRegistration(routingContext: RoutingContext)

    /**
     * handles dossier reading from a specific id.
     * @param routingContext
     * @see DossierDomainService
     * @return:
     *  - code 404 if given id was not found
     *  - code 200 if id was found, also with wanted dossier as Json Body
     *  - code 500 in cas of internal server errors
     */
    suspend fun handleDossierIdReading(routingContext: RoutingContext)

    /**
     * Handles dossier exam status update
     * Accepted events: THEORETICAL_EXAM_PASSED, PROVISIONAL_LICENSE_INVALIDATION, PRACTICAL_EXAM_PASSED.
     * @param routingContext
     * @see DossierDomainService
     * @return:
     *  - code 400 for bad request
     *  - code 200 for OK
     *  - code 500 for internal error
     */
    suspend fun handleDossierExamStatusUpdate(routingContext: RoutingContext)
}
