---
title: Ubiquitous Language
parent: Analisi del dominio
has_children: false
nav_order: 2
---


# Ubiquitous Language
Durante le fasi iniziali di sviluppo, per favorire la comunicazione tra esperti del dominio (cliente e stakeholders) e il team di sviluppo, l'obiettivo è stato quello di accordarsi sul significato dei termini utilizzati, evitando ambiguità ed incomprensioni.

Questo ha permesso di ottenere il seguente dizionario dei termini (successivamente utilizzati all'interno di tutte le fasi di sviluppo):

|Termine|Traduzione|Definizione|
|---|---|----------|
|Iscritto|Subscriber|Una persona che si iscrive in autoscuola. Viene associato ad uno o più dossier, di cui solamente uno valido in un certo momento.|
|Istruttore|Instructor|Un impiegato dell'autoscuola. Gestisce Driving Lessons con i Subscriber.|
|Veicolo|Vehicle|Mezzo con cui un Instructor esegue Driving Lesson.|
|Esame Teorico|Theoretical Exam|Esame teorico effettuato da un Subscriber.|
|Esame Pratico|Practical Exam|Esame ottenuto da un Subscriber, a bordo di un Vehicle insieme ad un Instructor.|
|Guida|Driving Lesson|Concordate tra Subscriber e Instructor. Univocamente associate ad un Subscriber, un Instructor ed un Vehicle.|
|Dottore|Doctor|Registra visite mediche a ciascun Subscriber, necessaria per conseguire il Theoretical Exam. Consente di ottenere un Theoretical Exam Pass.|
|Segretario|Secretary|Impiegato/a interno/a dell'autoscuola. Lavora solo in ufficio o a contatto con il pubblico.|
|Slot di guida|Driving Slot|Fascia oraria occupata ad una Driving Lesson o da un Practical Exam. Univocamente associato con un Subscriber, un Instructor ed un Vehicle.|
|Visita|Doctor Slot|Fascia oraria occupata per eseguire la visita col dottore, da parte di un Subscriber.|
|Pratica|Dossier|Insieme di informazioni riguardanti un singolo Subscriber.|
|Foglio rosa|Provisional License|Documento assegnato ad un Subscriber dopo il passaggio del Theoretical Exam, necessario per iscriversi al Practical Exam.|
|Programmazione delle guide|Driving lessons scheduling|Insieme di Driving Slots in una certa data.|
|Coda delle visite|Doctor scheduling|Insieme di Doctor Slots in una certa data e fascia oraria (che coincide con gli orari di disponibilità del dottore).|
|Appello d'esame|Exam appeal|Un appello per un Theoretical Exam, associato a più Dossier.|
|Completamento di esame Pratico/Teorico|Practical/Theoretical exam status|Un flag, che consenta di determinare con certezza lo stato del Dossier di un Subscriber|
|Registro esame teorico|Theoretical exam pass|Assegnato ad un Subscriber che abbia completato la visita col dottore, necessario per conseguire il Theoretical Exam.|
|Giorno d'esame pratico|Practical Exam Day|Identificativo dei giorni in cui è possibile inserire dei Driving Slots per effettuare Practical Exams.|
