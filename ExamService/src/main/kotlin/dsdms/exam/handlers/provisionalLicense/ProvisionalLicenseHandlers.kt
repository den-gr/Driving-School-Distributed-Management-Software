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
     * todo
     */
    suspend fun updateProvisionalLicenseHolder(routingContext: RoutingContext)
}
