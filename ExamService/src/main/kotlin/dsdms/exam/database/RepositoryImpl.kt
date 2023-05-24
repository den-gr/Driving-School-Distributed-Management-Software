package dsdms.exam.database

import com.mongodb.client.MongoDatabase
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import dsdms.exam.database.utils.RepositoryResponseStatus
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamDay
import dsdms.exam.model.entities.theoreticalExam.TheoreticalExamPass
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import java.time.LocalDate

class RepositoryImpl(examService: MongoDatabase) : Repository {
    private val theoreticalExamPassDb = examService.getCollection<TheoreticalExamPass>("TheoreticalExamPass")
    private val theoreticalExamDays = examService.getCollection<TheoreticalExamDay>("TheoreticalExamDay")

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

    override fun getFutureTheoreticalExamDays(): List<TheoreticalExamDay> {
        return theoreticalExamDays.find().toList().filter { el -> LocalDate.parse(el.date) > LocalDate.now() }
    }

    override fun insertTheoreticalExamDay(newExamDay: TheoreticalExamDay): RepositoryResponseStatus {
        return handleInsertResult(theoreticalExamDays.insertOne(newExamDay))
    }

    private fun handleInsertResult(insertOne: InsertOneResult): RepositoryResponseStatus {
        return if (insertOne.wasAcknowledged().not())
            RepositoryResponseStatus.INSERT_ERROR
        else RepositoryResponseStatus.OK
    }

    private fun handleDeleteResult(deleteOne: DeleteResult): RepositoryResponseStatus {
        return if (deleteOne.wasAcknowledged().not())
            RepositoryResponseStatus.DELETE_ERROR
        else if (deleteOne.deletedCount.toInt() == 0)
            RepositoryResponseStatus.PASS_NOT_FOUND_FOR_ID
        else RepositoryResponseStatus.OK
    }
}
