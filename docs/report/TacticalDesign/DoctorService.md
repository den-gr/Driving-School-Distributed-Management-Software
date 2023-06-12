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
  
<a href="#class_context">figura</a>
<a href="#class_communication">figura2</a>

## Doctor domain service

<div class="tip" markdown="1">Have **fun!**</div>

```mermaid {#class_context, align="center"}
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
<div align="center">[Fig 1] Diagramma della struttura del Doctor bounded contex</div>


## Doctor bounded context communicaiton



<div align="center">
<div id="class_communication" class="mermaid">
    classDiagram

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
</div>
<p>[Fig 2] Diagramma che mostra come Doctor bounded context comunica con DossierContext e </p>
</div>


## Sequence diagramm



```mermaid {#sequence_dossier}
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
<div align="center">[Fig 3] Diagramma di sequenza che mostra un scenario di successo della prenotazione di una visita dal dotore</div>


### Registrazione dei risultati 

```mermaid {#sequence_exam}
sequenceDiagram
    participant C as Client
    participant Doc as DoctorService
    participant E as ExamService
    participant DocDB as DoctorServiceDB

    C->>+Doc : saveDoctorResult(doctorResutl)

    Doc->>Doc : checkResutl(doctorResult)

    Doc-)+DocDB : <<create>>DoctorResult
    DocDB--)-Doc : OK

    Doc-)+E : notifyAboutDoctorApproval(event)
    Note right of E : create and store <br/> theoretical exam pass
    E--)-Doc : OK

    Doc--)-C : OK

```
<div align="center">[Fig 4] Diagramma di sequenza dove dopo registrazione di un risultato positivo della visita dal dottore viene avviata la creazione di registro esame teorico </div>