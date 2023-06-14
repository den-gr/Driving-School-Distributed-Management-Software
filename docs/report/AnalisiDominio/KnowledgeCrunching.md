---
title: Knowledge crunching
parent: Analisi del dominio
has_children: false
nav_order: 2
---


# Knowledge crunching

Dopo aver analizzato la richiesta che è stata effettuata e prodotto l’impact map si è tenuto la prima intervista con il committente, riportata di seguito.

<span style="color: #0077b6">**Analista**</span>: Nella sua richiesta, ci ha chiesto di sviluppare un backend da porre come base per digitalizzare i vostri processi aziendali, corretto ?

<span style="color: #79a355">**Cliente**</span>: Si, il front-end viene attualmente sviluppato da un team differente perciò abbiamo bisogno solo di back-end.

<span style="color: #0077b6">**Analista**</span>: Al momento attuale, i documenti come vengono gestiti ?

<span style="color: #79a355">**Cliente**</span>: Alla situazione attuale, disponiamo di un archivio cartaceo, contenente il fascicolo relativo a ciascun iscritto. Per la gestione delle guide, degli esami pratici e teorici e delle visite necessarie, ci avvaliamo di un calendario offline.

<span style="color: #0077b6">**Analista**</span>: Nell'archivio cartaceo, quali tipi di documenti vengono mantenuti per ogni studente ?

<span style="color: #79a355">**Cliente**</span>: Innanzitutto, identifichiamo gli utenti dell'autoscuola come Iscritti e non come studenti. Per ciascun iscritto manteniamo una pratica e un fascicolo.

<span style="color: #0077b6">**Analista**</span>: Cosa intendi con fascicolo ?

<span style="color: #79a355">**Cliente**</span>: Il fascicolo include la pratica e altri documenti a essa collegati, ad esempio il foglio rosa o il registro dell'esame teorico.

<span style="color: #0077b6">**Analista**</span>: E la pratica invece che cosa contiene ?

<span style="color: #79a355">**Cliente**</span>: La pratica include alcune informazioni di base relative all'iscritto, ad esempio Nome, Cognome, stato attuale di esami pratici e teorici.

<span style="color: #0077b6">**Analista**</span>: Parlando invece del calendario offline che attualmente utilizzate, mi pare di capire che sia un software, senza però collegamento in rete. Che informazioni vi vengono salvate ?

<span style="color: #79a355">**Cliente**</span>: Sul calendario, inseriamo la programmazione delle guide di ciascun iscritto, i giorni d'esame e la coda delle visite da effettuare con il dottore.

<span style="color: #0077b6">**Analista**</span>: Perciò quali processi sei interessato a digitalizzare ?

<span style="color: #79a355">**Cliente**</span>: Principalmente, vorrei digitalizzare e velocizzare le operazioni relative a:
- gestione delle guide
- gestione degli esami pratici, in termini d'iscrizioni, gestione dei tentativi
- gestione degli esami teorici, mantenendo se possibile un registro dell'esame teorico per ciascun iscritto
- gestione dei veicoli e degli istruttori, con i quali ciascuna guida è effettuata
- gestione maggiormente digitalizzata di pratiche e fascicoli, in modo da avere meno errori di consistenza e duplicazioni dei dati, a oggi purtroppo presenti


## Domain story telling
Per approfondire maggiormente la conoscenza del dominio, dei processi aziendali e di eventuali problemi riscontrati alla situazione attuale all'interno della azienda, si sono effettuati ulteriori colloqui con il cliente e gli esperti del dominio, maggiormente focalizzati sui processi precedentemente indicati.

Come strumento per la gestione e la rappresentazione grafica dei colloqui, si è deciso di utilizzare la strategia del Domain Storytelling, la quale prevede di lasciare raccontare agli esperti del dominio, come vengono svolte le differenti operazioni all'interno della Scuola Guida e cosa si vorrebbe ottenere come risultato, mentre i developers si occupano di catturare il racconto effettuato dall'esperto del dominio, tramite l'utilizzo del seguente linguaggio pittografico.

Per favorire le successive fasi di razionalizzazione e progettazione, i termini inseriti corrispondono all'[Ubiquitous Language](UbiquitousLanguage.md) discusso con gli esperti del dominio.

I risultati emersi da tali analisi, sono mostrati di seguito.

**User Story: registrazione di un nuovo iscritto**

<span style="color: #0077b6">**Analista**</span>: Allo stato attuale, come viene gestita la registrazione di un nuovo iscritto ?

<span style="color: #79a355">**Esperto del dominio**</span>: Quando un utente entra e richiede l'iscrizione per una determinata patente, gli vengono richieste alcune informazioni di base e la copia di un documento identificativo, da registrare nella relativa pratica, successivamente inserita nell'archivio cartaceo.

<div align="center">
      <img src="img/RegisterSubscriber.png" alt="
      domain story telling registrazione subscriber" >
      <p align="center" id="fig1">[Fig 1] Domain story telling: gestione attuale per la registrazione di un nuovo iscritto</p>
</div>

**User Story: inserimento di una guida**

<span style="color: #0077b6">**Analista**</span>: Attualmente, una nuova guida come viene registrata ? Chi la può registrare ?

<span style="color: #79a355">**Esperto del dominio**</span>: Quando un iscritto richiede una successiva guida all'istruttore, quest'ultimo controlla la programmazione delle guide e comunica all'iscritto le possibili date. Lui potrà quindi confermare una data tra quelle proposte e sarà l'istruttore che provvederà alla registrazione manuale di un nuovo slot di guida, presso l'ufficio.

<span style="color: #0077b6">**Analista**</span>: A parte giorno e orario, quali altre informazioni vengono associate a uno slot di guida ?

<span style="color: #79a355">**Esperto del dominio**</span>: Ciascuna guida, è univocamente collegata a un singolo Iscritto, un Istruttore e un Veicolo (oltre alle informazioni da voi elencate).

<div align="center">
      <img src="img/RegisterDrivingSlot.png" alt="
      domain story telling registrazione driving slot" >
      <p align="center" id="fig2">[Fig 2] Domain story telling: registrazione di uno slot di guida</p>
</div>

**User Story: gestione attuale degli esami teorici**

<span style="color: #0077b6">**Analista**</span>: Mi descrivi lo scenario principale e più tipico per la registrazione di un iscritto all'appello di un esame teorico ?

<span style="color: #79a355">**Esperto del dominio**</span>: Il caso tipico, è quello in cui l'iscritto non è ancora idoneo all'esame, cioè la visita medica non è ancora stata effettuata, perciò bisogna innanzitutto scegliere la data e l'orario della visita.

<span style="color: #0077b6">**Analista**</span>: Come vengono definite le date delle visite ?

<span style="color: #79a355">**Esperto del dominio**</span>: Il personale medico per ciascuna scuola guida, mette a disposizione alcune giornate e fascie orarie standard e predefinite. Per la mia esperienza di lavoro nel settore, queste informazioni non vengono mai modificate.

<span style="color: #0077b6">**Analista**</span>: Ok. Dopo aver fissato la visita medica, come si procede ?

<span style="color: #79a355">**Esperto del dominio**</span>: Il personale medico, successivamente alla visita, ne comunica il risultato. A questo punto viene assegnato alla pratica dell'iscritto un registro, per la gestione dei tentativi d'esame. L'iscritto viene quindi registrato al prossimo appello d'esame disponibile.

<div align="center">
      <img src="img/TheoreticalExam.png" alt="
      domain story telling Theoretical Exam" >
      <p align="center" id="fig3">[Fig 3] Domain story telling: gestione di un esame teorico</p>
</div>

**User Story: gestione attuale degli esami pratici**

<span style="color: #0077b6">**Analista**</span>: Quando un iscritto manifesta l'intenzione di effettuare un esame pratico, come si procede ?

<span style="color: #79a355">**Esperto del dominio**</span>: Come prima cosa verifichiamo due fattori principali:
- la programmazione passata delle guide per verificarne il numero esatto (almeno 12)
- il possedimento di un foglio rosa valido

Verificati questi fattori, si ricerca il primo slot di guida disponibile per effettuare l'esame pratico, e lo si propone all'iscritto.

<span style="color: #0077b6">**Analista**</span>: In precedenza hai parlato di slot di guida in riferimento a guide pratiche "standard". Qual è la differenza tra i due ?

<span style="color: #79a355">**Esperto del dominio**</span>: La principale differenza riguarda il fatto che gli slot relativi a esami pratici devono essere etichettati come tali e possono essere inseriti solo in determinati giorni da noi impostati. In tali giornate si hanno a disposizione uno o più esaminatori, che conducano gli esami.

<span style="color: #0077b6">**Analista**</span>: Se l'iscritto accetta le date a lui precedentemente proposte, come si procede ?

<span style="color: #79a355">**Esperto del dominio**</span>: Viene inserito nella programmazione un nuovo slot di guida, relativo all'esame in questione.

<div align="center">
      <img src="img/PracticalExam.png" alt="
      domain story telling Practical Exam" >
      <p align="center" id="fig4">[Fig 4] Domain story telling: gestione di un esame pratico</p>
</div>
