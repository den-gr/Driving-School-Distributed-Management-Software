package dsdms.driving.model.entities

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class DrivingSlot(
    @Contextual val _id: String? = null,
    // data, fascia oraria, istruttore, dossier, macchina
)

//@Serializable
//data class drivingSlotBooking(
//    // data, fascia oraria, id/nome istruttore, id dossier, ORDINARY/EXAM, targa macchina
//)

// Elemento di una enum che indica: ORDINARY, EXAM (come tipologia di guida)
// Un riferimento (id) ad un istruttore (tabella con istruttori)
// Una data ed una fascia oraria (30 minuti)
// Un riferimento (id) ad un veicolo

// primo passaggio: get di tutti gli slot già occupati in un certo giorno (parametro opzionale: id istruttore preferito)
// client: fa il "not" di questi risultati, ottenendo gli slot liberi
// sempre client: invia una richiesta con:
    // data e fascia oraria
    // id istruttore, targa vettura e id dossier
// qui il driving service controlla che l'ID del dossier abbia a se associata una provisional license valida, mediante exam service (inizialmente fasullo) (constraint per poter procedere)

// altro caso: esame pratico
// primo passaggio: get di tutti gli slot già occupati nel prossimo giorno d'esame (parametro opzionale: id istruttore preferito)


//servirà query per ottenere la lista di macchine
//servirà query per ottenere la lista di istruttori
//servirà query per ottenere il numero di guide effettuate da un certo DossierID

