---
title: Tactical Design
has_children: true
nav_order: 5
---
# Tactical design
L'architettura dei microservizi è stata progettata seguendo i principi di *[Clean Architecture](https://betterprogramming.pub/the-clean-architecture-beginners-guide-e4b7058c1165)* che permettono lo sviluppo di applicazioni Loosely-Coupled ovvero con forte disaccoppiamento dell’applicazione dall’infrastruttura.

In particolare sono stati individuati quattro layer: 
- **Frameworks & Drivers** layer: Implementa il punto d'ingresso utilizzato dal client del servizio per effettuare delle richieste utilizzando API REST esposte da ciascun microservizio, pertanto questo livello definisce gli endpoint REST supportati dal servizio insieme alla relativa interfaccia;
- **Interface Adapters** layer: Questo livello è composta da due tipi di componenti principali: gli adattatori d'input e gli adattatori di output;
  - *Gli adattatori d'input* sono responsabili della gestione delle comunicazioni proveniente dal mondo esterno, come le interfacce utente o i punti di accesso delle API, e della conversione di tale input in un formato comprensibile per la logica di business dell'applicazione. Essi ricevono l'input dell'utente e lo traducono in una forma che può essere elaborata dall'applicazione;
  - *Gli adattatori di output* gestiscono la comunicazione dall'applicazione verso il mondo esterno. Sono responsabili di convertire le richieste del modello di business in un formato riconoscibile dal Frameworks & Drivers layer. Gli adattatori di output interagiscono con il database e servizi esterni;
- **Application** layer o (Application Business Rules): Contiene una raccolta di tutti i casi d’uso del servizio in questione e le relative azioni per ciascuno di essi;
- **Domain** layer o (Enterprise Business Rules): Rappresenta il livello centrale di ogni servizio in cui sono definite le regole di business del contesto. Contiene la definizione di eventi, entity, value objects.


![Clean architecture diagram](img/cleanArchitecture.png)
<p align="center">[Fig 1] Clean Architecture nel contesto dei microservizi realizzati </p>

Per la realizzazione dei microservizi, sono state seguite le linee guida del [tactical design](https://thedomaindrivendesign.io/what-is-tactical-design/), cercando quindi d'individuare tra i concetti del dominio quali avessero il ruolo di entità, evento, value objects o domain service.

## Design dei microservizi

Il diagramma in figura <a href="#class_architecture">[Fig 2]</a> descrive le relazioni tra diverse componenti dell'architettura:

- ***Server***: responsabile per l'interazione con i client o altri sistemi esterni. Il server riceve le richieste in arrivo, tramite le API REST, e le inoltra al gestore delle rotte (*RouteHandlers*);

- ***RouteHandlers***: questa componente si occupa di gestire le richieste ricevute dal server e d'indirizzarle ai metodi del modello appropriati;

- ***Model***: questa componente contiene *Domain Services* che rappresentano e implementano la logica del modello di business;

- ***DomainService***: ogni servizio del dominio rappresentano una parte della logica del business del bounded context. Inoltre, i servizi del dominio possono dipendere da:
  - ***Repository***: questa componente rappresenta un'astrazione per l'accesso ai dati persistenti.
  - ***ChannelsProvider***: questa componente consente l'ottenimento dei canali di comunicazione con altri microservizi. (Vedi: <a href="#class_channels">[Fig 3]</a>)
  
<div id="class_architecture"></div>

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
<p align="center">[Fig 2] Struttura generale dei microservizi. </p>

### ChannelsProvider e tipi di risposte
***ChannelsProvider*** è una factory che produce e fornisce l'accesso agli adattatori di output, che permettono la comunicazione con gli altri microservizi. Ogni Channel è rappresentato da una interfaccia che fornisce i metodi coerenti con il modello di business, astraendo dall'aspetto tecnologico necessario per la comunicazione. 

Esempio dell'utilizzo: <a href="#class_channels">[Fig 3]</a>. Si può notare che i metodi dei canali restituiscono un'enumerazione *DomainResponseStatus* che permette da un lato la comprensione delle risposte provenienti da un altro dominio (es. DOSSIER_INVALID, VALID_DOSSIER_ALREADY_EXISTS ecc.), dall'altro la trasformazione dei problemi tecnologici (es. Errori di comunicazione) in un formato comprensibile dal dominio (es. DELETE_ERROR, UPDATE_ERROR).

Stesso approccio viene utilizzato anche per la *Repository* che tipicamente restituisce *RepositoryResponseStatus*, successivamente convertito in *DomainResponseStatus* tramite una mappa di conversione.
*RouteHandler* a sua volta ha una tabella per convertire le risposte del domino nei codici HTTP.

<div id="class_channels"></div>

```mermaid
classDiagram
direction TB

class ChannelsProvider{
    <<Interface>>
    DossierServiceChannel DossierChannel
    ExamServiceChannel ExamChannel
}

ChannelsProviderImpl --|> ChannelsProvider
ChannelsProviderImpl --> Vertx
<<Interface>> Vertx

class DossierServiceChannel{
    <<Interface>>
    checkDossierValidity(dossierId) DomainResponseStatus
}

class ExamServiceChannel{
    <<Interface>>
    notifyAboutDoctorApproval(DoctorApprovalEvent) DomainResponseStatus
}
ChannelsProvider --> DossierServiceChannel
ChannelsProvider --> ExamServiceChannel

```
<p align="center">[Fig 3] Esempio di comunicazione tra microservizi. In particolare sono mostrati i canali di comunicazione disponibili per DoctorService. </p>


## Organizzazione degli artefatti
I microservizi sono composti da quattro package principali:
- *channels* contiene gli artefatti responsabili per la comunicazione con altri microservizi;
- *database* package contenente gli artefatti che permettono l'accesso al database tramite Repository pattern;
- *handlers* raggruppa gli artefatti che gestiscono le rotte delle API REST;
- *model* contiene gli artefatti del modello del dominio.

![Alt text](<img/packageDiagram.png>)
<p align="center">[Fig 4] Diagramma dei package comune per tutti microservizi</p>


## Architettura generale
Seguendo l'architettura generale:
- il client comunica con uno o più microservizi tramite le API REST;
- Alcuni microservizi comunicano con altri microservizi tramite le API REST;
- Ogni microservizio, condivide un unico database, ma su schemi e tabelle separati e indipendenti tra loro.


![Alt text](<img/componentsDiagram.png>)

<p align="center">[Fig 5] Diagramma del deployment che visualizza un esempio dell'architettura generale dei nodi del sistema</p>