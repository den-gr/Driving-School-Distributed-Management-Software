package dsdms.exam.database

import dsdms.exam.database.utils.RepositoryResponseStatus
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

interface Repository {
    suspend fun dossierAlreadyHasOnePass(dossierId: String): Boolean
    suspend fun saveNewTheoreticalExamPass(theoreticalExamPass: TheoreticalExamPass): TheoreticalExamPass
    suspend fun getTheoreticalExamPass(dossierId: String): TheoreticalExamPass?
    suspend fun deleteTheoreticalExamPass(dossierId: String): RepositoryResponseStatus
    suspend fun getFutureTheoreticalExamAppeals(): List<TheoreticalExamAppeal>
    suspend fun insertTheoreticalExamDay(newExamDay: TheoreticalExamAppeal): RepositoryResponseStatus
    suspend fun updateExamAppeal(appealDate: String, appealList: List<String>): RepositoryResponseStatus

    suspend fun saveProvisionalLicenseHolder(provisionalLicenseHolder: ProvisionalLicenseHolder): RepositoryResponseStatus
    suspend fun findProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?
}
