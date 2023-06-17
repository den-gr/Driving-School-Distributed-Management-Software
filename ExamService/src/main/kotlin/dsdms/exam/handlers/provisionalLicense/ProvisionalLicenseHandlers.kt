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

package dsdms.exam.handlers.provisionalLicense

import dsdms.exam.handlers.Handlers
import io.vertx.ext.web.RoutingContext

/**
 * Implements logics to handle provisional license routes.
 */
interface ProvisionalLicenseHandlers : Handlers {

    /**
     * Handles registration of new provisional licenses.
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 409 -> PROVISIONAL_LICENSE_ALREADY_EXIST
     *  - 400, 500 -> Exceptions caught
     */
    suspend fun registerProvisionalLicence(routingContext: RoutingContext)

    /**
     * Handles getting requests of provisional license for a given dossier id.
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 404 -> ID_NOT_FOUND
     *  - 400, 500 -> Exceptions caught
     */
    suspend fun getProvisionalLicenseHolder(routingContext: RoutingContext)

    /**
     * Handle requests of checking provisional license validity.
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK or PROVISIONAL_LICENSE_NOT_VALID
     *  - 404 -> ID_NOT_FOUND that means that provisional license not found
     *  - 500 -> Exceptions caught
     */
    suspend fun isProvisionalLicenseValidHandler(routingContext: RoutingContext)

    /**
     * Allow register result of practical exam.
     * HTTP request must include a query parameter: "practicalExamUpdate"  that can be
     *  - PASSED if exam is passed. Delete provisional license and notify dossier service
     *  - FAILED if exam is failed. Increment number of practical exam attempts. Can delete provisional license
     *      and notify dossier service
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 202 -> DOSSIER_NOT_VALID
     *  - 409 -> PROVISIONAL_LICENSE_ALREADY_EXISTS
     *  - 404 -> DOSSIER_NOT_EXIST
     *  - 500 -> Exceptions caught, EXAM_STATUS_ERROR, DELETE_ERROR
     */
    suspend fun updateProvisionalLicenseHolder(routingContext: RoutingContext)
}
