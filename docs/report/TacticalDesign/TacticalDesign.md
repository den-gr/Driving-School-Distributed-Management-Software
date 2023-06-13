---
title: Tactical Design
has_children: true
nav_order: 5
---



```mermaid
classDiagram
direction LR
Server --> RouteHandlers
RouteHandlers --> Model
Model --> DomainServiceA
Model --> DomainServiceB
DomainServiceA --> Repository
DomainServiceA --> ChannelsProvider
DomainServiceB --> Repository

```
<p align="center">[Fig 1] Diagramma che mostra la struttura generale dei microservizi </p>



## ChannelsProvider
```mermaid
classDiagram
direction TB

class ChannelsProvider{
    <<Interfae>>
    DossierServiceChannel DossierChannel
    ExamServiceChannel ExamChannel

}

ChannelsProviderImpl --|> ChannelsProvider
ChannelsProviderImpl --> Vertx

class DossierServiceChannel{
    checkDossierValidity(dossierId) DomainResponseStatus
}

class ExamServiceChannel{
    notifyAboutDoctorApproval(DoctorApprovalEvent) DomainResponseStatus
}
ChannelsProvider --> DossierServiceChannel
ChannelsProvider --> ExamServiceChannel

```
<p align="center">[Fig 2] Diagramma che mostra un esempio come microservizi comunicano tra loro. In particolare sono mostrete i cannali di communicazione disponibili per DoctorService </p>