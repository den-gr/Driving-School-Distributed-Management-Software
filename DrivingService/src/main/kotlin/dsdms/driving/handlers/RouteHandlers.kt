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

package dsdms.driving.handlers

import io.vertx.ext.web.RoutingContext

/**
 * Set of HTTP routes handlers.
 */
interface RouteHandlers {

    /**
     * @see RouteHandlersImpl.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 400 -> INSTRUCTOR_NOT_FREE, OCCUPIED_DRIVING_SLOTS, VEHICLE_NOT_FREE, NOT_ENOUGH_DRIVING_LESSONS_FOR_EXAM,
     *          NOT_AN_EXAM_DAY, PROVISIONAL_LICENSE_NOT_VALID, NO_PROVISIONAL_LICENSE
     *  - 404 -> BAD_VEHICLE_INSTRUCTOR_INFO
     *  - 400, 500 -> exceptions caught
     */
    suspend fun registerNewDrivingSlot(routingContext: RoutingContext)

    /**
     * @see RouteHandlersImpl.handleException
     * @return http code:
     *  - 200 -> NO_SLOT_OCCUPIED or OK
     *  - Given slots in json body as encoded list
     *  - 400, 500 -> exceptions caught
     */
    suspend fun getOccupiedDrivingSlots(routingContext: RoutingContext)

    /**
     * @see RouteHandlersImpl.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 400 -> DELETE_ERROR
     *  - 400, 500 -> exceptions caught
     */
    suspend fun deleteDrivingSlot(routingContext: RoutingContext)

    /**
     * @see RouteHandlersImpl.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 400 -> ALREADY_DEFINED_AS_PRACTICAL_EXAM_DAY
     *  - 400, 500 -> exceptions caught
     */
    suspend fun postPracticalExamDay(routingContext: RoutingContext)

    /**
     * @see RouteHandlersImpl.handleException
     * @return http code:
     *  - 200 -> OK, with eventually a list of future practical exam days
     *  - 400, 500 -> exceptions caught
     */
    suspend fun getPracticalExamDays(routingContext: RoutingContext)
}
