package dsdms.dossier.database

import com.mongodb.client.MongoDatabase
import dsdms.dossier.model.Dossier
import org.litote.kmongo.eq
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection

class MongoDossier(dossierServiceDb: MongoDatabase): Repository {
    private val dossiers = dossierServiceDb.getCollection<Dossier>("Dossier")

    override fun createDossier(newDossier: Dossier): String? {
        return newDossier.apply { dossiers.insertOne(newDossier) }._id
    }

    override fun readDossierFromId(id: String): Dossier? {
        return dossiers.findOneById(id)
    }

    override fun readDossierFromCf(cf: String): List<Dossier> {
        return dossiers.find(Dossier::fiscal_code eq cf).toList()
    }

    override fun changeTheoreticalExamStatus(newStatus: Boolean, id: Int): Boolean? {
        TODO("Not yet implemented")
    }

    override fun changePracticalExamStatus(newStatus: Boolean, id: Int): Boolean? {
        TODO("Not yet implemented")
    }

}