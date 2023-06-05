package dsdms.doctor.handlers

import dsdms.doctor.model.domainServices.DoctorDomainService
import io.vertx.ext.web.RoutingContext

/**
 * Contains routes handlers.
 */
interface RouteHandlers {

    /**
     * To book a new doctor visit.
     * @see DoctorDomainService
     */
    suspend fun bookDoctorVisit(routingContext: RoutingContext)

    /**
     * To get booked doctor slots for a certain date.
     * @see DoctorDomainService
     */
    suspend fun getBookedDoctorSlots(routingContext: RoutingContext)

    /**
     * To delete a dossier's booked doctor slot.
     * @since for each dossierId can be booked only one doctor slot
     * @see DoctorDomainService
     */
    suspend fun deleteDoctorSlot(routingContext: RoutingContext)

    /**
     * To save a doctor visit result.
     * @see DoctorDomainService
     */
    suspend fun saveDoctorResult(routingContext: RoutingContext)
}
