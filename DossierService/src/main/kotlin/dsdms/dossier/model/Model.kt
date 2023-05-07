package dsdms.dossier.model

import dsdms.dossier.model.domainServices.DossierService

interface Model {
    val dossierService: DossierService
}
