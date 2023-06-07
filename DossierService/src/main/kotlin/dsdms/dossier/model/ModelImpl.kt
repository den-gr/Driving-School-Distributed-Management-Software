package dsdms.dossier.model

import dsdms.dossier.database.Repository
import dsdms.dossier.model.domainServices.DossierDomainService
import dsdms.dossier.model.domainServices.DossierDomainServiceImpl

/**
 * @param repository for connection with storage
 */
class ModelImpl(repository: Repository) : Model {
    override val dossierDomainService: DossierDomainService = DossierDomainServiceImpl(repository)
}
