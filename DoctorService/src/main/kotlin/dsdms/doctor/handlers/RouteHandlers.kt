package dsdms.doctor.handlers

import io.vertx.ext.web.RoutingContext

interface RouteHandlers {
    fun bookDoctorVisit(routingContext: RoutingContext)
    fun getBookedDoctorSlots(routingContext: RoutingContext)
    fun deleteDoctorSlot(routingContext: RoutingContext)
}
