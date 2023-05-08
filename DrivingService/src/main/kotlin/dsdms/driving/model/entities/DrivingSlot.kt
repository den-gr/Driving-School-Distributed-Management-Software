package dsdms.driving.model.entities

import dsdms.driving.model.valueObjects.DrivingSlotType
import dsdms.driving.model.valueObjects.licensePlate.LicensePlate
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class DrivingSlot(
    val date: String,
    val time: String,
    val instructorId: String,
    val dossierId: String,
    val licensePlate: LicensePlate,
    val slotType: DrivingSlotType,
    @Contextual val _id: String? = null,
)

// primo passaggio: get di tutti gli slot già occupati in un certo giorno (parametro opzionale: id istruttore preferito)
// client: fa il "not" di questi risultati, ottenendo gli slot liberi
// sempre client: invia una richiesta con:
// data e fascia oraria
// id istruttore, targa vettura e id dossier
// qui il driving service controlla che l'ID del dossier abbia a se associata una provisional license valida, mediante exam service (inizialmente fasullo) (constraint per poter procedere)

// altro caso: esame pratico
// primo passaggio: get di tutti gli slot già occupati nel prossimo giorno d'esame (parametro opzionale: id istruttore preferito)

// servirà query per ottenere la lista di macchine
// servirà query per ottenere la lista di istruttori
// servirà query per ottenere il numero di guide effettuate da un certo DossierID
