---
title: Dossier Service 
has_children: false
parent: Tactical Design
nav_order: 1
---

# DossierService tactical design

- **Entities**: Dossier
- **Value objects**:  ExamsStatus, SubscriberDocuments
- **Events**: ExamEvent


## Dossier domain service

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

DossierDomainService --> Repository
DossierDomainService --> Dossier
DossierDomainService --> ExamEvent : receive
DossierDomainService --> SubscriberDocuments
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

```

<p align="center">[Fig 1] Diagramma dell'organizzazione del Dossier bounded context</p>

## Dossier

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