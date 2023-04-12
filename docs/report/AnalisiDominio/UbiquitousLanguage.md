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
|Provisional License|Documento assegnato ad un Subscriber dopo il passaggio del Theoretical Exam.|
|Driving lessons scheduling|Insieme di Driving Slots  in una certa data.|
|Exam appeal|Un appello per un Theoretical Exam, associato a più Dossier.|
|Practical/Theoretical exam status|Un flag, che consenta di determinare con certezza lo stato del Dossier di un Subscriber|
|Theoretical exam pass|Assegnato ad un Subscriber che abbia completato la visita col dottore, necessario per conseguire il Theoretical Exam.|








Valid for 1 year, max 3 attempts at the practical exam within the year of validity, then a new Theoretical exam is required. If the second Provisional License expires, a new Dossier is required.

Provides 2 attempts, within 6 months, to take the Theoretical exam. After 6 months or after the second failure, the pass will be invalid and it will be necessary to redo the visit with the Doctor