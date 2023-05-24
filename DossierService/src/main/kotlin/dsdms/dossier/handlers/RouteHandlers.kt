package dsdms.dossier.handlers

import io.vertx.ext.web.RoutingContext
import kotlinx.coroutines.DelicateCoroutinesApi
import dsdms.dossier.model.domainServices.DossierService

@DelicateCoroutinesApi
interface RouteHandlers {

    /**
     * @param routingContext
     * Handles new dossier registration
     * @see DossierService
     * @return:
     *  - code 400 for bad request is something happen during decoding of json body
     *  - code 200 with id of created dossier
     */
    suspend fun handleDossierRegistration(routingContext: RoutingContext)

    /**
     * @param routingContext
     * handles dossier reading from a specific id
     * @see DossierService
     * @return:
     *  - code 404 if given id was not found
     *  - code 200 if id was found, also with wanted dossier as Json Body
     */
    suspend fun handleDossierIdReading(routingContext: RoutingContext)

    /**
     * @param routingContext
     * handles dossier exam status update
     * @see DossierService
     * @return:
     *  - code 400 for bad request
     *  - code 200 for OK
     *  - code 500 for internal error
     */
    suspend fun handleDossierExamStatusUpdate(routingContext: RoutingContext)

    /**
     * @param routingContext
     * handles dossier exam attempts update
     * @see DossierService
     * @return:
     *  - code 400 for bad request
     *  - code 200 for OK
     *  - code 500 for internal error
     */
    suspend fun handleDossierExamAttemptsUpdate(routingContext: RoutingContext)
}
