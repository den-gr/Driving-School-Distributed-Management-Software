package dsdms.driving.handlers

import io.vertx.ext.web.RoutingContext

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
