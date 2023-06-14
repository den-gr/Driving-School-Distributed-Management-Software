---
title: Exam Service 
has_children: false
parent: Tactical Design
nav_order: 4
---



# ExamService tactical design
- **Entities**: TheoreticalExamAppeal, TheoreticalExamPass, ProvisionalLicense
- **Value objects**: ProvisionalLicenseHolder, TheoreticalExamAppealUpdate
- **Events**: DoctorApprovalEvent, ExamEvent



## Provisional license domain service
```mermaid
classDiagram

class ProvisionalLicenseDomainService {
    <<Interface>>
    registerProvisionalLicense(ProvisionalLicense pl) DomainResponseStatus
    getProvisionalLicenseHolder(String dossierId) ProvisionalLicenseHolder?
    isProvisionalLicenseValid(String dossierId, Date date): 
    incrementProvisionalLicenseFailures(String dossierId) DomainResponseStatus
    practicalExamSuccess(String dossierId) DomainResponseStatus
}

ProvisionalLicenseDomainService --> ProvisionalLicenseHolder
ProvisionalLicenseDomainService --> ExamEvent : produce
ProvisionalLicenseDomainService --> DossierServiceChannel 

DossierServiceChannel --> ExamEvent

class ProvisionalLicenseHolder {
    Int practicalExamAttempts
    ProvisionalLicense provisionalLicense
}

ProvisionalLicenseHolder o-- "1" ProvisionalLicese

class ProvisionalLicese {
    String dossierId
    Date startValidity
    Date endValidity
}

class ExamEvent {
    <<Enumeration>>
    THEORETICAL_EXAM_PASSED
    PROVISIONAL_LICENSE_INVALIDATION
    PRACTICAL_EXAM_PASSED
}

class DossierServiceChannel {
    <<Interface>>
    updateExamStatus(String dossierId, ExamEVent event) DomainResponseStatus
    checkDossierValidity(String dossierId) DomainResponseStatus

}

```
<p align="center">[Fig 1] Diagramma di domain service che gestisce le operazioni che riguardano il foglio rossa </p>

## Theoretical exam domain service

```mermaid
classDiagram

class ExamDomainService {
    <<Interface>>
    saveNewTheoreticalExamPass(DoctorApprovalEvent docs) InsertTheoreticalExamPassResult
    readTheoreticalExamPass(String dossierId) TheoreticalExamPass?
    deleteTheoreticalExamPass(String dossierId) DomainResponseStatus
    insertNewExamAppeal(TheoreticalExamAppeal newExamDay) DomainResponseStatus
    getNextExamAppeals() NextTheoreticalExamAppeals
    putDossierInExamAppeal(TheoreticalExamAppealUpdate update) DomainResponseStatus
}

ExamDomainService --> TheoreticalExamAppeal
ExamDomainService --> TheoreticalExamPass
ExamDomainService --> DoctorApprovalEvent : receive
ExamDomainService --> ExamEvent : produce 

class ExamEvent {
    <<Enumeration>>
    THEORETICAL_EXAM_PASSED
    PROVISIONAL_LICENSE_INVALIDATION
    PRACTICAL_EXAM_PASSED
}

class DoctorApprovalEvent {
    String dossierId
    String date
}


class TheoreticalExamAppeal {
    String date
    Int numberOfPlaces
    List~String~ registeredDossiers
    String initTime
    String finishTime
}

class TheoreticalExamPass {
    String dossierId
    String releaseDate
    String expiryDate
    Int remainingAttempts
}

```
<p align="center">[Fig 2] Diagramma di domain service che copre gli aspetti che riguardano l'esame teorico</p>


## Sequence diagram

```mermaid
sequenceDiagram
    participant C as Client
    participant E as ExamService
    participant Dos as DossierService
    participant EDB as ExamServiceDB

    C ->>+ E : updateProvisionalLicense("PASSED")

    E -)+ Dos : isDossierValid(dossierId)
    Dos -) Dos : checkValidity(dossierId)
    Dos --)- E : OK

    E -)+ EDB : <<delete>>ProvisionalLicense
    EDB --)- E : OK

    E --)- C : OK

```
<p align="center">[Fig 3] Diagramma di sequenza che descrive l'operazione di registrazione del risultato positivo del esame pratico</p>