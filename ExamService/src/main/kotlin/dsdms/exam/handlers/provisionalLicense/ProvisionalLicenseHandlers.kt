package dsdms.exam.handlers.provisionalLicense

import dsdms.exam.handlers.Handlers
import io.vertx.ext.web.RoutingContext

interface ProvisionalLicenseHandlers : Handlers {

    /**
     * Handles registration of new provisional licenses
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 409 -> PROVISIONAL_LICENSE_ALREADY_EXIST
     *  - 400, 500 -> Exceptions caught
     */
    suspend fun registerProvisionalLicence(routingContext: RoutingContext)

    /**
     * Handles getting of provisional license for given dossier id, if available
     * @see Handlers.handleException
     * @return http code:
     *  - 200 -> OK
     *  - 404 -> ID_NOT_FOUND
     *  - 400, 500 -> Exceptions caught
     */
    suspend fun getProvisionalLicenseHolder(routingContext: RoutingContext)

    /**
     * Handle requests of checking provisional license validity
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
