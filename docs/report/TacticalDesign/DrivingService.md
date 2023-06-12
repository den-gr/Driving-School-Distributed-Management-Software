---
title: Driving Service 
has_children: false
parent: Tactical Design
nav_order: 3
---

# DrivingService tactical design
- **Entities**: DrivingSlot, Instructor, Vechicle
- **Value objects**: DrivingSlotBooking, DrivingSlotsRequst, LicensePlate, PracticalExamDay

## Domain services of driving bounded context

```mermaid
classDiagram
direction LR

class DrivingDomainService {
    <<Interface>>
    saveNewDrivingSlot(DrivingSlotBooking docs) DrivingSlotRegistrationResult
    getOccupiedDrivingSlots(DrivingSlotsRequest docs) DrivingSlotsRequestResult
    deleteDrivingSlot(String drivingSlotId) DomainResponseStatus
}


DrivingDomainService --> DrivingSlotsRequst
class DrivingSlotsRequst {
    LocalDate date
    Sting?   instructorId
}

class PracticalExamDomainService {
    <<Interface>>
    registerPracticalExamDay(PracticalExamDay day) DomainResponseStatus
    getPracticalExamDays() PracticalExamDaysResponse 
}

DrivingDomainService --> Repository
PracticalExamDomainService --> Repository

```

### Uno

```mermaid
classDiagram
direction TB

class DrivingDomainServiceImpl {
    saveNewDrivingSlot(DrivingSlotBooking docs) DrivingSlotRegistrationResult
    getOccupiedDrivingSlots(DrivingSlotsRequest docs) DrivingSlotsRequestResult
    deleteDrivingSlot(String drivingSlotId) DomainResponseStatus
}

DrivingDomainServiceImpl --> DrivingSlot
DrivingDomainServiceImpl --> Vechicle
DrivingDomainServiceImpl --> Instructor
ExamServiceChannel <-- DrivingDomainServiceImpl 



class DrivingSlot {
    String date
    String time
    String instructorId
    String dossierId
    LicensePlate licensePlate
    DrivingSlotType type
    String id
}

DrivingSlot --> LicensePlate
DrivingSlot --> DrivingSlotType

LicensePlate : String licensePlate

class DrivingSlotType { 
    <<Enumeration>>
    ORDINARY
    EXAM
}

class Instructor {
    String name
    String surname
    String fiscalCode
    String id
}

class Vechicle {
    LicensePlate licensePlate
    String manufactured
    String model
    Int year
}



Vechicle --> LicensePlate : Identified by



class ExamServiceChannel {
    isProvisionalLicenseValid(String dossierId, Date date) DomainResponseStatus
}

```

## Sequence diagramm
