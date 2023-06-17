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

package dsdms.exam.handlers.theoreticalExams

import dsdms.exam.handlers.Handlers
import io.vertx.ext.web.RoutingContext

/**
 * Implements logics to handle theoretical exam routes.
 */
interface TheoreticalExamHandlers : Handlers {

    /**
     * Handles theoretical exam pass creation.
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 400 -> EXAM_PASS_ALREADY_AVAILABLE, exception caught
     *  - 500 -> other exceptions
     */
    suspend fun createTheoreticalExamPass(routingContext: RoutingContext)

    /**
     * Handles getting of theoretical exam passes if available for given dossier id.
     * @see Handlers.handleException
     * @return http code:
     *  - 404 -> ID_NOT_FOUND
     *  - 200 -> OK with theoretical exam pass as Json Body
     *  - 400, 500 -> exceptions caught
     */
    suspend fun getTheoreticalExamPass(routingContext: RoutingContext)

    /**
     * Handles deleting of theoretical exam pass if available for given dossier id.
     * @see Handlers.handleException
     * @return http code:
     *  - 500 -> DELETE_ERROR, exceptions caught
     *  - 404 -> ID_NOT_FOUND
     *  - 200 -> OK
     */
    suspend fun deleteTheoreticalExamPass(routingContext: RoutingContext)

    /**
     * Handles registration for a new theoretical exam appeal.
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 400 -> DATE_ALREADY_IN, exception caught
     *  - 500 -> other exceptions or INSERT_ERROR
     */
    suspend fun createNewTheoreticalExamAppeal(routingContext: RoutingContext)

    /**
     * Handles getting of next exam appeals.
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK or NO_EXAM_APPEALS with founded list of exam appeals as Json Body
     */
    suspend fun getNextTheoreticalExamAppeals(routingContext: RoutingContext)

    /**
     * Handles dossier id registration to a specific appeal.
     * @see Handlers.handleException
     * @return http code:
     *  - 400 -> APPEAL_NOT_FOUND, DOSSIER_ALREADY_PUT, PLACES_FINISHED, exceptions caught
     *  - 500 -> UPDATE_ERROR, exceptions caught
     *  - 200 -> OK
     */
    suspend fun putDossierInExamAppeal(routingContext: RoutingContext)
}
