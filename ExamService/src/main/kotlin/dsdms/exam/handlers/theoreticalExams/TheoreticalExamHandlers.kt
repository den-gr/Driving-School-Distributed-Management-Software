package dsdms.exam.handlers.theoreticalExams

import dsdms.exam.handlers.Handlers
import io.vertx.ext.web.RoutingContext

interface TheoreticalExamHandlers : Handlers {

    /**
     * Handles theoretical exam pass creation
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 400 -> EXAM_PASS_ALREADY_AVAILABLE, exception caught
     *  - 500 -> other exceptions
     */
    suspend fun createTheoreticalExamPass(routingContext: RoutingContext)

    /**
     * Handles getting of theoretical exam passes if available for given dossier id
     * @see Handlers.handleException
     * @return http code:
     *  - 404 -> ID_NOT_FOUND
     *  - 200 -> OK with theoretical exam pass as Json Body
     *  - 400, 500 -> exceptions caught
     */
    suspend fun getTheoreticalExamPass(routingContext: RoutingContext)

    /**
     * Handles deleting of theoretical exam pass if available for given dossier id
     * @see Handlers.handleException
     * @return http code:
     *  - 500 -> DELETE_ERROR, exceptions caught
     *  - 404 -> ID_NOT_FOUND, exceptions caught
     *  - 200 -> OK
     */
    suspend fun deleteTheoreticalExamPass(routingContext: RoutingContext)

    /**
     * Handles registration for a new theoretical exam appeal
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 400 -> DATE_ALREADY_IN, exception caught
     *  - 500 -> other exceptions or INSERT_ERROR
     */
    suspend fun createNewTheoreticalExamAppeal(routingContext: RoutingContext)

    /**
     * Handles getting of next exam appeals
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK or NO_EXAM_APPEALS with founded list of exam appeals as Json Body
     */
    suspend fun getNextTheoreticalExamAppeals(routingContext: RoutingContext)

    /**
     * Handles dossier id registration to a specific appeal
     * @see Handlers.handleException
     * @return http code:
     *  - 400 -> APPEAL_NOT_FOUND, DOSSIER_ALREADY_PUT, PLACES_FINISHED, exceptions caught
     *  - 500 -> UPDATE_ERROR, exceptions caught
     *  - 200 -> OK
     */
    suspend fun putDossierInExamAppeal(routingContext: RoutingContext)
}
