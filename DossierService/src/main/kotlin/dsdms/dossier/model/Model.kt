package dsdms.dossier.model

import dsdms.dossier.model.domainServices.DossierService

interface Model {

    /**
     * Representing actually used model implementation
     */
    val dossierService: DossierService
}
