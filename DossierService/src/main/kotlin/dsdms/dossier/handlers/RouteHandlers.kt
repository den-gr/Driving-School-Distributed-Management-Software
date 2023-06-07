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
