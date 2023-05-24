package dsdms.exam.database

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import dsdms.exam.database.utils.RepositoryResponseStatus
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class RepositoryImpl(examService: MongoDatabase) : Repository {
    private val theoreticalExamPassDb = examService.getCollection<TheoreticalExamPass>("TheoreticalExamPass")

    override fun dossierAlreadyHasOnePass(dossierId: String): Boolean {
        return theoreticalExamPassDb.find(TheoreticalExamPass::dossierId eq dossierId).toList().isNotEmpty()
    }

    override fun saveNewTheoreticalExamPass(theoreticalExamPass: TheoreticalExamPass): TheoreticalExamPass {
        return theoreticalExamPass.apply { theoreticalExamPassDb.insertOne(theoreticalExamPass) }
    }

    override fun getTheoreticalExamPass(dossierId: String): TheoreticalExamPass? {
        return theoreticalExamPassDb.findOne(TheoreticalExamPass::dossierId eq dossierId)
    }

    override fun deleteTheoreticalExamPass(dossierId: String): RepositoryResponseStatus {
        return handleDeleteResult(theoreticalExamPassDb.deleteOne(TheoreticalExamPass::dossierId eq dossierId))
    }

    private fun handleDeleteResult(deleteOne: DeleteResult): RepositoryResponseStatus {
        return if (deleteOne.wasAcknowledged().not())
            RepositoryResponseStatus.DELETE_ERROR
        else if (deleteOne.deletedCount.toInt() == 0)
            RepositoryResponseStatus.PASS_NOT_FOUND_FOR_ID
        else RepositoryResponseStatus.OK
    }
}
