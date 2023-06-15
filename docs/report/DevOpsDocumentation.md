---
title: Documentazione DevOps
has_children: false
nav_order: 8
---

# DevOps Documentation


## Build Automation
### Struttura multi-progetto
Il progetto è stato sviluppato come un gradle multi progetto composto da 5 sottoprogetti. Quatro di questi sottoprogetti sono dedicati a ciascun bounded context individuato,  mentre un altro sottoprogetto denominato *SystemTester* è stato creato per simulare un client e testare l'intero sistema di microservizi. 
Questo approccio presenta diversi vantaggi. Innanzitutto, consente di ottenere una struttura modulare e separata, che è fondamentale per lo sviluppo dei microservizi. Ogni bounded context viene gestito come un sottoprogetto indipendente, consentendo agli sviluppatori di concentrarsi sulle funzionalità specifiche di ciascun microservizio.

Inoltre, grazie all'approccio multiproject, è possibile condividere il codice di configurazione di Gradle tra i vari sottoprogetti e importare alcune classi nel sottoprogetto "SystemTester", semplificando il processo di testing. Questa condivisione del codice di configurazione aiuta a ridurre la duplicazione e a mantenere una coerenza nelle impostazioni di build e dipendenze tra i vari sottoprogetti.

### Task personalizzati
Per ogni sottoprogetto sono stati creati dei task personalizzati e sono stati inseriti all’interno dei relativi file `build.gradle.kts`.

In particolare per ogni sottoprogetto sono stati configurati o creati vari task, due più principali tra loro sono:
- `ShadowJar` permette generare un jar eseguibile. La configurazione per ogni sottoproggeto
  - Specifica il Main class
  - Assegna un nome personalizzato che può includere la versione del sistema.
  - Specifica la cartella di destinazioen
- `createJavadoc` permette generare gli artefatti con la documentazione Javadoc
## Continuous Integration
Particolare attenzione è stata posta nell’individuazione di misure per assicurare la qualità del codice del sistema. Grazie alle GitHub Actions sono stati predisposti dei workflow per garantire la Continuous Integration e la Quality Assurance

Sono stati creati due workflow
- **SwaggerHub publication** è composto da un singolo job che pubblica la documentazione openapi su [SwaggerHub](https://swagger.io/tools/swaggerhub/)
- **Main workflow** ha il compito creare gli artefatti ed effettuare il testing. In alcuni casi fa la pubblicazione degli artefatti.
  
### SwaggerHub publication
La documentazione delle api REST viene fatta utilizzando le specifiche [OpenAPI](https://swagger.io/specification/). Per la scrittura e manutenzione più comoda di questa documentazione è stato deciso dividere il file principale in più file. Il processo di unione dei file e la pubblicazione automatica richiede automatizzazione.

In particolare per avere sempre online la documentazione più recente è stato creato il workflow *SwaggerHub publication* che automaticamente aggiorna la versione [latest](https://app.swaggerhub.com/apis/DenGuzawr22/DSDMS/latest) della documentazione.
Il workflow è composto da:
1. Generazione di un unico file.yaml a partire dai fragmenti
2. Validazione della documentazione che permette di rilevare gli errori
3. Pubblicazione del file assemblato su SwaggerHub
    

### Main workflow
Il workflow principale è composta da tre job:
1. **BuildAndTest** questo job è più fondamentale del processo di CI. Include questi step:
   - Setup di kotlin e di java
   - Building di tutti sottoprogetti, questo step include esecuzione dei test locali e la verifica di QA.
   - Deployment dei microservizi nei container docker insieme con un container di MongoDB utilizzando docker-compose.
   - Esecuzione dei test di *SystemTester*
2. **Release**: se il workflow è stato scatenato nel main branch sarà partito il processo di release degli artefatti su GitHub. Per il release viene utilizato il [semantic-release](https://github.com/semantic-release/semantic-release) che genera automaticamente una versione del [Semantic Versioning](https://semver.org).
3. **SwaggerHubPublish** questo job ripete la funzionalità di job del workflow descritto in precedenza con due differenze:
   1. Le API non vengono più aggiornati ma viene creata una nuova versione.
   2. La versione del release del job precedente viene utilizzata per nominare la versione delle API
   
### DRY in GitHub Actions
Per quanto in due workflow dal progetto vengono utilizzati due job molto simili è stato creato un Action riusabile per ridurre la duplicazione del codice.
Questo action riceve seguenti parametri:
- `source-file` il percorso al entry file dei fragmenti della documentazione openapi
- `version` la versione delle API che devono essere creati o aggiornati 
- `type` l'operazione che può essere "create" o "update"
- `token` token d'accesso di SwaggerHub


## Quality Assurance
Per garantire la qualità del codice e dello sviluppo, sono stati utilizzati i seguenti plugin del Gradle:
- **Gradle pre-commit Git Hooks**: Questo plugin crea un Git Hook che impedisce di effettuare un commit se non viene rispettato il formato dei [Conventional commits](https://www.conventionalcommits.org/en/v1.0.0/). I "Conventional Commits" sono una convenzione per scrivere messaggi di commit strutturati in un formato specifico, che facilita l'automazione delle versioni e la generazione delle note di release. 
- **Ktlint**:  Questo plugin permette di mantenere uno stile uniforme nel codice Kotlin. Ktlint applica regole di formattazione del codice predefinite o personalizzate all'interno del progetto. Assicurarsi che tutto il codice Kotlin sia formattato in modo coerente facilita la leggibilità del codice e promuove buone pratiche di sviluppo.
- **Detect**: Questo plugin consente di eseguire un'analisi statica del codice per identificare e prevenire diversi tipi di problemi potenziali, per esempio,  il plugin può individuare vulnerabilità nella sicurezza del codice, dipendenze non conformi, violazioni delle best practice di sviluppo e altre problematiche simili.
