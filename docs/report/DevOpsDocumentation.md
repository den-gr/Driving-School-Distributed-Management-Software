---
title: Documentazione DevOps
has_children: false
nav_order: 8
---

# DevOps Documentation


## Build Automation
### Struttura multi-progetto
Il progetto è stato sviluppato come un gradle multi progetto composto da cinque sotto-progetti. Quattro di questi sono dedicati a ciascun bounded context individuato, mentre un ultimo denominato *SystemTester* è stato creato per simulare un client e testare l'intero sistema di microservizi. 
Questo approccio presenta diversi vantaggi:
- Innanzitutto, consente di ottenere una struttura modulare e separata, fondamentale per lo sviluppo dei microservizi;
- Ogni bounded context viene gestito in modo indipendente, consentendo agli sviluppatori di concentrarsi sulle funzionalità specifiche di ciascun microservizio.

Inoltre, grazie all'approccio multi-project, è possibile condividere il codice di configurazione di Gradle tra i vari sotto-progetti e importare alcune classi su "SystemTester", semplificando il processo di testing. Questa condivisione del codice di configurazione aiuta a ridurre la duplicazione e a mantenere una coerenza nelle impostazioni di build e dipendenze.

### Task personalizzati
Per ogni sotto-progetto sono stati creati dei task personalizzati e sono stati inseriti all’interno dei relativi file `build.gradle.kts`.

In particolare per ciascuno sono stati configurati o creati vari task, tra cui:
- `ShadowJar` che permette la generazione di un jar eseguibile. La configurazione:
  - Specifica la Main class;
  - Assegna un nome personalizzato che può includere la versione del sistema;
  - Specifica la cartella di destinazione.
- `createJavadoc` permette la generazione degli artefatti assieme alla documentazione Javadoc.

## Continuous Integration
Particolare attenzione è stata posta nell’individuazione di misure per assicurare la qualità del codice. Grazie alle GitHub Actions sono stati predisposti dei workflow per garantire la Continuous Integration e la Quality Assurance.

Sono stati creati due workflow:
- **SwaggerHub publication** composto da un singolo job che pubblica la documentazione OpenApi su [SwaggerHub](https://swagger.io/tools/swaggerhub/)
- **Main workflow** ha il compito di creare gli artefatti (in alcuni casi pubblicandoli) ed effettuare il testing.
  
### SwaggerHub publication
La documentazione delle Api REST viene fatta utilizzando le specifiche [OpenAPI](https://swagger.io/specification/). Per uno sviluppo e una manutenzione semplificata di questa documentazione si è deciso di dividere il file principale in più file. Il processo di unione dei file e la pubblicazione automatica richiede automatizzazione.

In particolare per avere sempre online la documentazione più recente è stato creato il workflow *SwaggerHub publication* che automaticamente aggiorna la versione [latest](https://app.swaggerhub.com/apis/DenGuzawr22/DSDMS/latest) della documentazione.
Il workflow è composto da:
1. Generazione di un unico file.yaml a partire dai frammenti;
2. Validazione della documentazione che ne permette la rilevazione e la correzione di errori;
3. Pubblicazione del file assemblato su SwaggerHub.
    

### Main workflow
Il workflow principale è composta da tre job:
1. **BuildAndTest**, fondamentale nel processo di CI. Include i seguenti step:
   - Setup di kotlin e di java;
   - Building di tutti sotto-progetti, questo step include esecuzione dei test locali e la verifica di QA;
   - Deployment dei microservizi nei container docker insieme con un container di MongoDB utilizzando docker-compose;
   - Esecuzione dei test di *SystemTester*.
2. **Release**: se il workflow è stato scatenato nel main branch sarà partito il processo di release degli artefatti su GitHub. Per la release viene utilizzato [semantic-release](https://github.com/semantic-release/semantic-release). Quest'ultimo consente la generazione automatica di una versione del [Semantic Versioning](https://semver.org).
3. **SwaggerHubPublish** questo job ripete la funzionalità di job del workflow descritto in precedenza. Con due differenze:
   1. Le API non vengono più aggiornati ma viene creata una nuova versione;
   2. La versione di release del job precedente viene utilizzata per nominare la versione delle API.
   
### DRY in GitHub Actions
In due workflow dal progetto vengono utilizzati due job molto simili, per questo motivo si è creato un Action riusabile per ridurre la duplicazione del codice aumentandone la semplicità di lettura e manutenibilità futura.
Questa Action riceve i seguenti parametri:
- `source-file` il percorso al entry file dei frammenti della documentazione openapi;
- `version` la versione delle API che devono essere create o aggiornate;
- `type` l'operazione, che può essere "create" o "update";
- `token` token d'accesso di SwaggerHub.


## Quality Assurance
Per garantire la qualità del codice e dello sviluppo, sono stati utilizzati i seguenti Gradle Plugin:
- **Gradle pre-commit Git Hooks**: Questo plugin crea un Git Hook che impedisce di effettuare un commit se non viene rispettato il formato [Conventional commits](https://www.conventionalcommits.org/en/v1.0.0/). I "Conventional Commits" sono una convenzione per scrivere messaggi di commit strutturati in un formato specifico, che facilita l'automazione delle versioni e la generazione delle note di release. 
- **Ktlint**: Questo plugin permette di mantenere uno stile uniforme nel codice Kotlin. Ktlint applica regole di formattazione del codice predefinite o personalizzate all'interno del progetto. Assicurarsi che tutto il codice Kotlin sia formattato in modo coerente facilita la leggibilità del codice e promuove buone pratiche di sviluppo.
- **Detect**: Questo plugin consente di eseguire un'analisi statica del codice per identificare e prevenire diversi tipi di potenziali problemi. Ad esempio, il plugin può individuare vulnerabilità nella sicurezza del codice, dipendenze non conformi, violazioni delle best practice di sviluppo e altre problematiche simili.
