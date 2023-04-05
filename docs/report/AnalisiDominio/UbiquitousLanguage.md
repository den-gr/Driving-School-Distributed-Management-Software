# Ubiquitous Language
Durante le fasi iniziali di sviluppo, per favorire la comunicazione tra esperti del dominio (cliente e stakeholders) e il team di sviluppo, l'obiettivo è stato quello di accordarsi sul significato dei termini utilizzati, evitando ambiguità ed incomprensioni.

Questo ha permesso di ottenere il seguente dizionario dei termini (successivamente utilizzati all'interno di tutte le fasi di sviluppo):

|Term|Definition|
|----|----------|
|Subscriber|Una persona che si iscrive in autoscuola. Viene associato ad uno o più dossier, di cui solamente uno valido in un certo momento.|
|Instructor|Un impiegato dell'autoscuola. Gestisce Driving Lessons con i Subscriber.|
|Vehicle|Mezzo con cui un Instructor esegue Driving Lesson.|
|Theoretical Exam|Esame teorico effettuato da un Subscriber.|
|Practical Exam|Esame ottenuto da un Subscriber, a bordo di un Vehicle insieme ad un Instructor.|
|Driving Lesson|Concordate tra Subscriber e Instructor. Univocamente associate ad un Subscriber, un Instructor ed un Vehicle.|
|Doctor|Registra visite mediche a ciascun Subscriber, necessaria per conseguire il Theoretical Exam. Consente di ottenere un Theoretical Exam Pass.|
|Secretary|Impiegato/a interno/a dell'autoscuola. Lavora solo in ufficio o a contatto con il pubblico.|
|Driving Slot|Fascia oraria occupata ad una Driving Lesson o da un Practical Exam. Univocamente associato con un Subscriber, un Instructor ed un Vehicle.|
|Doctor Slot|Fascia oraria occupata per eseguire la visita col dottore, da parte di un Subscriber.|
|Dossier|Insieme di informazioni riguardanti un singolo Subscriber.|
|Provisional License|Documento assegnato ad un Subscriber dopo il passaggio del Theoretical Exam, necessario per iscriversi al Practical Exam.|
|Driving lessons scheduling|Insieme di Driving Slots in una certa data.|
|Doctor scheduling|Insieme di Doctor Slots in una certa data e fascia oraria (che coincide con gli orari di disponibilità del dottore).|
|Exam appeal|Un appello per un Theoretical Exam, associato a più Dossier.|
|Practical/Theoretical exam status|Un flag, che consenta di determinare con certezza lo stato del Dossier di un Subscriber|
|Theoretical exam pass|Assegnato ad un Subscriber che abbia completato la visita col dottore, necessario per conseguire il Theoretical Exam.|
|Practical Exam Day|Identificativo dei giorni in cui è possibile inserire dei Driving Slots per effettuare Practical Exams.|

---
Altri contenuti:
- [Analisi del dominio](AnalisiDominio.md)
- [Knowledge Crunching](KnowledgeCrunching.md)
- [Ritorna all'indice](../../Index.md)