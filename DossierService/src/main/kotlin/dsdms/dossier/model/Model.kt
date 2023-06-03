package dsdms.dossier.model

import dsdms.dossier.model.domainServices.DossierService

/**
 * Main model of the system.
 */
interface Model {

    /**
     * Representing actually used model implementation.
     */
    val dossierService: DossierService
}
