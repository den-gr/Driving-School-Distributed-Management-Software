---
title: Strumenti utilizzati
has_children: false
nav_order: 6
---

# Strumenti utilizzati

In questa sezione inseriamo le tecnologie che sono state utilizzate all'interno del progetto. Data la natura e architettura similare dei microservizi, non sono presenti tecnologie specifiche per ciascun microservizio.

Di seguito le tecnologie utilizzate.

## Gradle

Per favorire il testing, la creazione e la distribuzione del software, si è deciso di utilizzare Gradle come strumento di build automation. Il processo di building è quindi semplificato dall'utilizzo di tale strumento, che mette a disposizione differenti plugin, dipendenze e librerie utilizzate nel progetto. In particolare mediante Gradle si è incluso nel progetto diversi tool per la quality assurance (successivamente descritti) o il necessario per l'utilizzo della libreria KotlinX.

Inoltre per favorire ulteriormente l'organizzazione e lo sviluppo del software e delle dipendenze/plugin di Gradle, si è deciso di utilizzare Toml. Quest'ultimo consente di raggruppare gli elementi prima indicati in un unico file, separato da ciascun microservizio. Le dipendenze/plugin sono poi importate dal file Toml in ciascun Build.gralde.

## Docker

Per favorire una corretta esecuzione del progetto indipendentemente dal sistema operativo e dalle risorse del sistema, si è deciso di utilizzare Docker come ambiente di esecuzione. Questo ha permesso e permette di eseguire ciascun Microservizio in modo indipendente, insieme a MongoDb e ad altri servizi utili allo sviluppo e all'esecuzione del software.

In particolare ciascun microservizio dispone di un Docker file, che ne definisce per l'esecuzione alcuni parametri, argomenti e variabili d'ambiente fondamentali (tra cui l'URI di mongodb o la porta in cui il microservizio sarà istanziato su localhost), e il comando per il caricamento del singolo Jar, all'interno del microservizio in questione.

Infine, nella directory generale, è presente un file Docker Compose, utilizzato per l'effettivo avvio del progetto.

## Quality Assurance: Detekt e Ktlint

Durante e in particolare al termine del progetto, per verificare e mantenere sotto controllo la qualità del codice inserita nel progetto, si sono utilizzati due tool: Detekt e Ktlint

Ktlint è uno strumento open-source sviluppato per formattare e controllare la qualità del codice scritto in Kotlin. Utilizza una serie di regole di formattazione e di stile codice per garantire la coerenza e la leggibilità del codice. In sostanza, Ktlint è uno strumento di analisi statica del codice che può essere utilizzato da sviluppatori Kotlin per aiutarli a scrivere un codice più pulito e ben organizzato.

Kotlin Detekt è uno strumento di analisi statica del codice sorgente Kotlin, che aiuta gli sviluppatori a identificare potenziali problemi di codice, bug, vulnerabilità di sicurezza e altre violazioni delle best practice di sviluppo. Detekt è progettato per integrarsi facilmente con diversi ambienti di sviluppo, come IntelliJ ed Eclipse, e può essere eseguito come parte del processo di compilazione del progetto o come plugin Gradle.

## K-Mongo e MongoDB

MongoDB è un sistema di gestione di database non relazionale (NoSQL) che consente di archiviare e gestire grandi volumi di dati non strutturati, in formato Json. È progettato per essere scalabile, flessibile e ad alte prestazioni, fornendo alta manipolazione mediante query e diverse funzionalità di aggregazione dei dati.

KMongo è una libreria di MongoDB per Kotlin che fornisce un'API intuitiva e facile da usare per interagire con MongoDB, sul linguaggio di programmazione Kotlin, utilizzato nel progetto in questione. KMongo fornisce inoltre supporto all'aggregazione dei dati, alle operazioni effettuabili su MongoDB e alle Kotlin Coroutines, fornendo metodi Suspend per l'utilizzo delle funzioni fornite da MongoDB, in modo asincrono.

## Vertx per WebClient e WebServer

VertX WebClient e WebServer sono due componenti della piattaforma Vert.x. Un framework per la creazione e la gestione di applicazioni web-server, che include tra le altre, i componenti Web Client e Web Server.

Vert.x WebClient è una libreria client per effettuare chiamate HTTP/HTTPS a server remoti. È basato sulla Reactive Streams API e utilizza un modello asincrono di programmazione. Tale funzionalità è utilizzata, nelle comunicazioni tra System tester e i microservizi o nelle chiamate ad API solo tra microservizi.

Vert.x WebServer, invece, è un server per la creazione di applicazioni web, con l'utilizzo di un modello asincrono di programmazione. Inoltre, supporta la gestione delle richieste HTTP/HTTPS e la creazione di API REST. Ciascun MicroServizio utilizza l'implementazione di VertX WebServer: Coroutine Verticle.

Questo ha consentito l'utilizzo da un lato delle funzionalità offerte dal WebServer di VertX (es. Gestione delle routes), e dall'altro ha permesso di usufruire dei vantaggi nell'utilizzo delle Kotlin Coroutines come modello asincrono su Kotlin. In particolare in tutti i microservizi si sono utilizzati i metodi Suspend, una tipologia di Coroutines che permettono a ciascuna funzione di essere sospesa e ripresa in qualsiasi momento, favorendo meccanismi asincroni di programmazione.

## KotlinX per serializzare e deserializzare

KotlinX è un insieme di librerie Kotlin che forniscono supporto per la serializzazione e la deserializzazione di oggetti in diversi formati. Le librerie forniscono un'API per la manipolazione dei dati in modo efficiente, consentendo l'utilizzo di Data Class, fornendo al codice un grado di semplicità e leggibilità maggiore, anche per sviluppi futuri.
Tutte queste funzionalità sono risultate particolarmente utili nel progetto in esame, dove la serializzazione e la deserializzazione sono spesso necessarie per comunicare tra microservizi e client. In generale, KotlinX semplifica la gestione dei dati distribuiti, migliorando l'efficienza e la sicurezza del codice.

## Semantic Release per Automatic Versioning e Release

Semantic Release è un framework che gestisce la versione del software e la sua pubblicazione in modo automatico, seguendo le linee guida del semantic release. Utilizza il concetto di Conventional Commits per standardizzare le descrizioni dei commit e sfrutta gli strumenti di continuous integration per automatizzare il rilascio di nuove versioni in base ai cambiamenti effettuati nel codice sorgente. In questo modo, Semantic Release garantisce un processo di rilascio del software più sicuro e affidabile.

Nel progetto questo tool è stato utilizzato per effettuare le release del software sulla piattaforma GitHub.

## Swagger e OpenAPI

Swagger è uno strumento open-source per la documentazione delle API in formato JSON o YAML. Consente di creare una documentazione completa delle API, definendone i dati d'input e output, gli endpoint e le operazioni che la API può eseguire.

Nel progetto è stato utilizzato per documentare tutte le API, che ciascun microservizio mette a disposizione, comprendendo ogni tipo di response code, payload e message, che viene possibile ricevere.
Inoltre per favorire le procedure di test delle API in futuro, per il cliente e utenti finali, ogni rotta a disposizione è disponibile per il testing.

Tutta la documentazione è stata inoltre pubblicata e resa pubblica presso SwaggerHub, disponibile e consultabile al seguente link: https://app.swaggerhub.com/apis/DenGuzawr22/DSDMS/latest.

## Cucumber

Cucumber è un framework che semplifica la creazione di Acceptance Test, mediante l'utilizzo di un linguaggio chiaro e non ambiguo, Gherkin, interpretato da Scenari e Step.
L'intero progetto è stato eseguito con l'obiettivo dell'Acceptance Test Driven Development, quindi focalizzato sui test di accettazione.
Tali test, sono focalizzati sull'utente finale del sistema e sui requisiti funzionali e non funzionali imposti al progetto.

L'utilizzo di questo Framework ha consentito l'inclusione del cliente nella definizione e implementazione delle procedure di testing, consentendo a entrambe le parti, team e cliente, di trarne vantaggio. Da un lato il cliente si rendeva conto del grado di complessità e di completezza del progetto (relativamente ai requisiti imposti), dall'altro il team ha potuto incrementare non poco la conoscenza sul dominio in esame, traendo spunto e informazione dal cliente.

Per maggiori informazioni sull'utilizzo di questa tecnologia all'interno del progetto, consultare la sezione [Testing](Testing.md) 