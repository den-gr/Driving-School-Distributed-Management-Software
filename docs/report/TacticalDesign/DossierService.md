---
title: Dossier Service 
has_children: false
parent: Tactical Design
nav_order: 1
---

# DossierService tactical design

- **Entities**: Dossier
- **Value objects**:  ExamStatus, SubscriberDocuments
- **Events**: ExamEvent
- **Specifications**: SubscriberControls


## Dossier domain service
**DossierService** nel suo *Model* contiene il domain service *DossierDomainService* che ha i compiti: 
- Registrare le nuove pratiche degli iscritti e verificare che i dati forniti dal client sono conformi con il modello del dominio.
- Fornire le pratiche in caso delle richiese
- Reagire all'*ExamEvent* gestendo la logica di aggiornamento del macro stato degli esami (*ExamSatatus") che include invalidazione della pratica in caso se il secondo foglio rossa era invalidato.

Il modello dominio ha una singola entità *Dossier* che contiene l'informazione della pratica. Per crearla il client deve inviare un value object *SubscriberDocuments* che viene validato tramite le specifiche *SubscriberControls*

```mermaid
classDiagram
direction TB


class DossierDomainService {
    <<Interface>>
    saveNewDossier(SubscriberDocuments docs): SaveDossierResult
    readDossierFromId(String id): GetDossierResult
    updateExamStatus(ExamEvent event, String id): DomainResponseStatus
}

class Repository 
<<Interface>> Repository 

class SubscriberControls 
<<Interface>> SubscriberControls 

DossierDomainService --> Repository : use
DossierDomainService --> Dossier : create and manage
DossierDomainService --> ExamEvent : receive
DossierDomainService --> SubscriberDocuments : receive
DossierDomainService --> SubscriberControls 

SubscriberControls --> SubscriberDocuments : verify

class SubscriberDocuments{
    String name
    String surname
    String birthdate
    String fiscal_code

}

class Dossier{
    String name
    String surname
    String birthdate
    String fiscal_code
    Boolen validity
    String id
    ExamStatus: exStatus
}
class ExamEvent{
    <<Enumeration>>
    THEORETICAL_EXAM_PASSED
    PROVISIONAL_LICENSE_INVALIDATION
    PRACTICAL_EXAM_PASSED
}

class SubscriberControls {
    checkFiscalCode(docs: SubscriberDocuments)
    checkBithdate(docs: SubscriberDocuments)
    isNumeric(String str)
}

```

<p align="center">[Fig 1] Diagramma del modello di dominio di DossierService</p>

## Dossier

Ogni *Dossier* contiene un value object *ExamStatus* che contiene due campi:
- *theoreticalExamState* che mostra se iscritto ha pasatto l'esame teorico.
- *practicalExamState* che mostra se iscritto ha passato l'esame teorico e se ha gìà avuto invalidazioni del foglio rossa. Questo campo non contine il numero di fallimenti per ogni singolo foglio rossa, lasciando questa responsabilità a *ProvisionalLicenseHolder* del ExamContext

*ExamStatus* incapsula un insieme di invarianti che impediscono la creazione degli stati inconsisteni nel modello del dominio (esempio: l'esame pratico è passato mentre l'esame teorico non è passato).

```mermaid

classDiagram
direction LR

Dossier o-- "1" ExamStatus

class ExamStatus{
    TheoreticalExamState  thState
    PracticalExamState  prState
    registerProvisionalLicenceInvalidation() ExamStatus
    registerPracticalExamPassed() ExamStatus
    registerTheoreticalExamPassed() ExamsStatus
}

ExamStatus --> PracticalExamState
ExamStatus --> TheoreticalExamState



class PracticalExamState {
    <<Enumeration>>
    TO_DO
    FIRST_PROVISIONAL_LICENCE_INVALID
    SECOND_PROVISIONAL_LICENCE_INVALID
    PASSED
}


class TheoreticalExamState {
    <<Enumeration>>
    TO_DO
    PASSED
}

```
<p align="center">[Fig 2] Diagramma che descrive la classe ExamStatus che appartiene alla classe Dossier </p>