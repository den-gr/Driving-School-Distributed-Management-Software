---
title: Ubiquitous Language
parent: Analisi del dominio
has_children: false
nav_order: 3
---


# Ubiquitous Language
Durante le fasi iniziali di sviluppo, per favorire la comunicazione tra esperti del dominio (cliente e stakeholders) e il team di sviluppo, l'obiettivo è stato quello di accordarsi sul significato dei termini utilizzati, evitando ambiguità e incomprensioni.

Questo ha permesso di ottenere il seguente dizionario dei termini (successivamente utilizzati all'interno di tutte le fasi di sviluppo):

| Termine                         | Traduzione                 | Definizione                                                                                                                                 |
|---------------------------------|----------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| Iscritto                        | Subscriber                 | Una persona che si iscrive in autoscuola. Viene associato ad uno o più dossier, di cui solamente uno valido in un certo momento.            |
| Istruttore                      | Instructor                 | Un impiegato dell'autoscuola. Gestisce Driving Lessons con i Subscriber.                                                                    |
| Veicolo                         | Vehicle                    | Mezzo con cui un Instructor esegue Driving Lesson.                                                                                          |
| Esame Teorico                   | Theoretical Exam           | Esame teorico effettuato da un Subscriber.                                                                                                  |
| Esame Pratico                   | Practical Exam             | Esame ottenuto da un Subscriber, a bordo di un Vehicle insieme ad un Instructor.                                                            |
| Guida                           | Driving Lesson             | Una lezione di guida. Concordata tra Subscriber e Instructor.                                                                               |
| Dottore                         | Doctor                     | Registra visite mediche a ciascun Subscriber, necessaria per conseguire il Theoretical Exam. Consente di ottenere un Theoretical Exam Pass. |
| Segretario                      | Secretary                  | Impiegato/a interno/a dell'autoscuola. Lavora solo in ufficio o a contatto con il pubblico.                                                 |
| Slot di guida                   | Driving Slot               | Fascia oraria occupata ad una Driving Lesson o da un Practical Exam. Univocamente associato con un Subscriber, un Instructor ed un Vehicle. |
| Visita                          | Doctor Slot                | Fascia oraria occupata per eseguire la visita col dottore, da parte di un Subscriber.                                                       |
| Pratica                         | Dossier                    | Insieme di informazioni riguardanti un singolo Subscriber.                                                                                  |
| Foglio rosa                     | Provisional License        | Documento assegnato ad un Subscriber dopo il passaggio del Theoretical Exam, necessario per iscriversi al Practical Exam.                   |
| Appello d'esame                 | Exam appeal                | Un appello per un Theoretical Exam, associato a più Dossier.                                                                                |
| Stasus d'esame                  | Exam status                | Una sezione del Dossier che documenta i progressi negli esami.                                                                              |
| Registro esame teorico          | Theoretical exam pass      | Assegnato ad un Subscriber che abbia completato la visita col dottore, necessario per conseguire il Theoretical Exam.                       |
| Giorno d'esame pratico          | Practical Exam Day         | Identificativo dei giorni in cui è possibile inserire dei Driving Slots per effettuare Practical Exams.                                     |
| Guida pratica regolare          | Regular driving slot       | Identificativo delle guide pratiche regolari (che non rappresentano un esame pratico)                                                       |
| Guida d'esame                   | Exam driving slot          | Identificativo delle guide pratiche, rappresentanti un esame pratico di guida                                                               |
| Evento di approvazione del dottore | Doctor approval event      | Un evento quando il dottore approva lo stato del salute del iscritto permetendo la creazione del registro esame teorico                     |  
 | Evento d'esame                  | Exam event                 | Un evento d'esame che provoca una modifica della pratica                                                                                    |  