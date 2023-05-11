package dsdms.dossier.model.domainServices

import dsdms.dossier.database.utils.RepositoryResponseStatus
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.ExamStatusUpdate
import dsdms.dossier.model.valueObjects.SubscriberDocuments

interface DossierService {
    suspend fun saveNewDossier(givenDocuments: SubscriberDocuments): String?

    suspend fun verifyDocuments(documents: SubscriberDocuments): DomainResponseStatus

    suspend fun readDossierFromId(id: String): Dossier?

    suspend fun updateExamStatus(data: ExamStatusUpdate, id: String): RepositoryResponseStatus
}