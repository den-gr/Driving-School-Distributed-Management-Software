package dsdms.dossier.model.domainServices.subscriberCheck

import dsdms.dossier.database.Repository
import dsdms.dossier.model.entities.Dossier
import dsdms.dossier.model.valueObjects.SubscriberDocuments
import kotlinx.datetime.toJavaLocalDate
import java.time.Period

/**
 * Allows to verify correctness of subscriber date.
 */
class SubscriberControlsImpl : SubscriberControls {
    override suspend fun checkDuplicatedFiscalCode(
        givenDocuments: SubscriberDocuments,
        repository: Repository,
    ): Boolean {
        val alreadyExistingDossiers: List<Dossier> = repository.readDossierFromCf(givenDocuments.fiscal_code)
        return alreadyExistingDossiers.count { el -> el.validity } != 0
    }

    override suspend fun checkSubscriberBirthdate(
        givenDocuments: SubscriberDocuments,
        repository: Repository,
    ): Boolean {
        return Period.between(
            givenDocuments.birthdate.toJavaLocalDate(),
            java.time.LocalDate.now(),
        ).years < SubscriberConstants.MIN_AGE
    }

    /**
     * At least one given string matches the regex, it means that is a number, not a real string.
     */
    override suspend fun isNumeric(vararg string: String): Boolean {
        return string.any { el -> el.matches(Regex(".*[0-9].*")) }
    }
}
