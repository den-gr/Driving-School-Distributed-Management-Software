package dsdms.exam.database

import dsdms.exam.database.utils.RepositoryResponseStatus
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamAppeal
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import dsdms.exam.model.valueObjects.ProvisionalLicenseHolder

interface Repository {
    fun dossierAlreadyHasOnePass(dossierId: String): Boolean
    fun saveNewTheoreticalExamPass(theoreticalExamPass: TheoreticalExamPass): TheoreticalExamPass
    fun getTheoreticalExamPass(dossierId: String): TheoreticalExamPass?
    fun deleteTheoreticalExamPass(dossierId: String): RepositoryResponseStatus
    fun getFutureTheoreticalExamAppeals(): List<TheoreticalExamAppeal>
    fun insertTheoreticalExamDay(newExamDay: TheoreticalExamAppeal): RepositoryResponseStatus
    fun updateExamAppeal(appealDate: String, appealList: List<String>): RepositoryResponseStatus

    fun saveProvisionalLicenseHolder(provisionalLicenseHolder: ProvisionalLicenseHolder): RepositoryResponseStatus
    fun findProvisionalLicenseHolder(dossierId: String): ProvisionalLicenseHolder?
}
