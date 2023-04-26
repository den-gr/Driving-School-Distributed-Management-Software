package dsdms.dossier.model

import dsdms.dossier.database.Repository
import dsdms.dossier.model.domainServices.DossierService

class ModelImpl(repository: Repository) : Model {
    override val dossierService: DossierService = DossierService(repository)
}