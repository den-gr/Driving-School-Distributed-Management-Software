---
title: Doctor Service 
has_children: false
parent: Tactical Design
nav_order: 2
---


# DoctorService tactical design

- **Entities**: DoctorSlot
- **Value objects**: DoctorResutl
- **Events**: DoctorApprovalEvent


## Doctor domain service

```mermaid
classDiagram

class DoctorDomainService{
    <<Interface>>
    saveDoctorSlot(DoctorSlot doctorSlot) InsertDoctorVisitResult
    getOccupiedDoctorSlots(String date) BookedDoctorSlots
    deleteDoctorSlot(String dossierId) DomainResponseStatus
    saveDoctorResult(DoctorResult doctorResult) DomainResponseStatus
}

DoctorDomainService --> DoctorSlot
DoctorDomainService --> DoctorResult
DoctorDomainService --> Repository



class DoctorSlot {
    String date
    String time
    String dossierId
}

class DoctorResult {
    String dossierId
    String date
    ResultTypes result
}


DoctorResult --> ResultTypes

class ResultTypes {
    <<Enumeration>>
    VALID
    NEED_ONE_MORE_VISIT
    NOT_VALID
}

```
<div id="class_context" align="center">[Fig 1] Diagramma della struttura del Doctor bounded contex</div>


## Doctor bounded context communicaiton

```mermaid
classDiagram
direction TB
class DoctorDomainService{
    <<Interface>>
}

DoctorDomainService <|-- DoctorDomainServiceImpl
DoctorDomainServiceImpl --> DossierServiceChannel
DoctorDomainServiceImpl --> ExamServiceChannel 



class DossierServiceChannel {
    <<Interface>>
    checkDossierValidity(String dossierId) DomainResponseStatus
}

class ExamServiceChannel {
    <<Interface>>
    notifyAboutDoctorApproval(DoctorApprovalEvent event) DomainResponseStatus
}


DoctorDomainServiceImpl --> DoctorApprovalEvent : produce
DoctorApprovalEvent <-- ExamServiceChannel

class DoctorApprovalEvent {
    String dossierId
    String date
}

```
<div id="class_communication" align="center">[Fig 2] Diagramma che mostra come Doctor bounded context comunica con DossierContext e </div>

## Sequence diagramm


```mermaid
sequenceDiagram 

    participant C as Client
    participant Doc as DoctorService
    participant Dos as DossierService
    participant DocDB as DoctorServiceDB

    C->>+Doc : bookDoctorVisit(doctorSlot)
    Doc->>Doc : CheckDoctorSlotAvailability(doctorSlot)
    Note left of Doc : assume check is passed
    Doc-)+Dos : isDossierValid()
    Dos--)-Doc : OK

    Doc-)+DocDB : <<create>>DoctorSlot
    DocDB--)-Doc : OK


    Doc--)-C : OK
```
<div id="sequence_dossier" align="center">[Fig 3] Diagramma di sequenza che mostra un scenario di successo della prenotazione di una visita dal dotore</div>


### Registrazione dei risultati 
```mermaid
sequenceDiagram


```