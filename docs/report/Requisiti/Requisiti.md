---
title: Requisiti
has_children: false
nav_order: 3
---


# Requisiti

Questa sezione della relazione, viene dedicata alla descrizione approfondita dei requisiti di progetto, raccolti durante le fasi di analisi del dominio e perfezionati durante le fasi di Knowledge Crunching.

Questo ha permesso di determinare nel dettaglio, le funzionalità necessarie e volute all'interno del progetto, evitando ambiguità nella discussione con il cliente, appoggiandosi sull'Ubiquitous Language precedentemente discusso.

## Requisiti di Business
I requisiti di business specificano le caratteristiche che il sistema dovrà possedere per essere corretto, definendone gli obiettivi di alto livello.

I requisiti di business previsti dal Driving School Management Software, che sono stati individuati, sono i seguenti:
- digitalizzare i processi di business attualmente cartacei;
- ridurre i tempi di ogni operazione;
- consentire l'utilizzo di alcuni applicativi (es. prenotazione di guide pratiche) da remoto;

## Requisiti utente
I requisiti utente esprimono i bisogni degli utenti e descrivono quali sono le azioni che l’utente deve poter effettuare interagendo con il sistema.

Dalla precedente analisi del dominio che è stata effettuata possiamo rilevare i seguenti requisiti utente:
- registrare un nuovo Dossier;
- leggere ed aggiornare un Dossier esistente;
- leggere e verificare la validità di Provisional License e Theoretical Exam Pass;
- registrare un nuovo Driving Slot (relativo ad una Driving Lesson o ad un Practical Exam);
- eliminare un Driving Slot esistente;
- registrare un Dossier valido ed esistente, all'interno di un Exam Appeal;
- aggiornare o creare Theoretical Exam Pass o Provisional License;
- registrare e leggere Doctor Slot;
- selezionare e leggere Practical Exam Day;

## Requisiti funzionali
I requisiti funzionali riguardano le funzionalità che il sistema deve mettere a disposizione all’utente. Per la loro definizione è necessario basarsi sui requisiti utente estratti in precedenza.

Sono stati identificati i seguenti requisiti funzionali, per i diversi Bounded Contexts individuati nella precedente fase di analisi del dominio: Exam Context, Driving Context e Dossier Context.

### Dossier Context
- dopo la seconda scadenza del Provisional License (limitatamente ad un singolo Dossier), il dossier deve essere invalidato;
- un dossier invalidato non deve essere utilizzabile su alcun processo effettuato dall'utente, ma viene mantenuto col solo scopo di condurre analisi future;
- un subscriber può avere più dossier a lui collegati, ma solamente uno valido in un dato momento;
- deve essere possibile aggiornare Theoretical Exam e Practical Exam Status;
- è possibile inserire un Driving Slot relativo a Practical Exam, solo in un Practical Exam Day;

Optional: un dossier può essere invalidato manualmente, tramite una chiamata ad API

### Exam Context
- ciascun dossier può avere un'unica Provisional License valida in un dato momento;
- per poter inserire un Dossier in un Exam Appeal è necessario un Theoretical Exam Pass valido;
- l'ottenimento di un Theoretical Exam Pass, avviene conseguentemente alla visita col Dottore;
- ciascun Theoretical Exam Pass permette l'iscrizione a massimo due Exam Appeal, nell'arco di sei mesi di validità;
- per consentire l'inserimento di un Driving Slot (relativo a Driving Lessons o Practical Exams), è necessario un Provisional License valido;
- ciascun Provisional License permette l'iscrizione a massimo tre Practical Exam, entro un anno di validità;
- il Dossier Context, dovrà essere notificato di ciascuna scadenza del Provisional License
- il Provisional license viene fornito dopo il successo del Theoretical exam;

### Driving Context
- ogni Driving Slot, in una fascia oraria è univocamente associato ad un Dossier, un Instructor e un Vehicle;
- ciascun Driving Slot può rappresentare un Practical Exam o una Driving Lesson;
- ciascun Dossier può riservare un Driving slot alla volta;
- per poter inserire un Driving Slot, è necessario verificare la validità della Provisiona License

Optional: un Vehicle dopo un certo numero di Driving Slot a lui associati, dovrà procedere ad un controllo.

## Requisiti non funzionali
I requisiti non funzionali riguardano le funzionalità che il sistema non deve necessariamente possedere per fare in modo che sia funzionante e corretto.

I requisiti non funzionali previsti dal Driving School Management Software, che sono stati individuati, sono i seguenti:
- il software deve essere cross-platform, cioè eseguibile sia su sistemi operativi Windows che su MacOS e Linux, o comunque su qualsiasi sistema operativo capace di supportare Java Runtime Environment versione 16 e successive;
- il sistema deve essere integrabile con front-end esterni, utilizzando le API messe a disposizione;

## Requisiti implementativi
I requisiti di implementazione vincolano l’intera fase di realizzazione del sistema, ad esempio richiedendo l’uso di uno specifico linguaggio di programmazione e/o di uno specifico tool software.

I requisiti implementativi previsti dal Driving School Management Software, che sono stati indicati dal cliente, sono i seguenti:
- le componenti software del sistema saranno programmate in linguaggio Kotlin;
- il software deve essere basato sull'architettura di base dei Microservizi;;
- le API (Application Programming Interface) fornite da ciascun microservizio, dovranno essere di tipo REST;
- il deployment di ciascun microservizio, dovrà avvenire mediante l'utilizzo di Container Docker;
- il testing del sistema dovrà avvenire mediante Acceptance Testing;

---
Altri contenuti:
- [Ritorna all'indice](../../index.md)