package dsdms.doctor.handlers

import io.vertx.ext.web.RoutingContext
import dsdms.doctor.model.domainServices.DoctorService

interface RouteHandlers {

    /**
     * To book a new doctor visit
     * @see DoctorService
     */
    suspend fun bookDoctorVisit(routingContext: RoutingContext)

    /**
     * To get booked doctor slots for a certain dossier id
     * @see DoctorService
     */
    suspend fun getBookedDoctorSlots(routingContext: RoutingContext)

    /**
     * To delete a dossier id's booked doctor slot
     * @since each dossierId can book only one doctor slot
     * @see DoctorService
     */
    suspend fun deleteDoctorSlot(routingContext: RoutingContext)

    /**
     * To save a doctor visit result
     * @see DoctorService
     */
    suspend fun saveDoctorResult(routingContext: RoutingContext)
}
