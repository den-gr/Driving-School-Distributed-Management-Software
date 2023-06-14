---
title: Sviluppi Futuri
has_children: false
parent: Conclusioni
nav_order: 9
---

# Sviluppi Futuri

Il progetto sviluppato rappresenta un insieme di API, a disposizione di scuole guida, per l'informatizzazione delle funzioni svolte.
Per questo motivo l'espansione, gli sviluppi e utilizzi futuri di questo progetto, ne rappresentano una porzione fondamentale, di cui si è tenuto conto già dalle prime fasi progettuali.

Per quanto concerne il dominio affrontato, si è deciso con il committente di gestire solamente quanto è necessario per patenti B, lasciando quindi spazio ad allargamenti futuri verso corsi e patenti professionali.

Un esempio su tutti, con minimo sforzo implementativo, sarebbe possibile introdurre gli elementi per l'informatizzazione delle procedure necessarie alle patenti Moto. Questo in quanto molti degli elementi necessari sono già presenti, e i constraints temporali sono i medesimi.

Infine, mediante l'utilizzo dell'interfaccia grafica (che il committente aveva già assegnato), sarà possibile un utilizzo user friendly e affidabile delle API messe a disposizione.
Ad esempio per l'utilizzo del sistema di prenotazione dei Driving Slot, sono disponibili API con cui ottenere gli slot attualmente occupati. Diviene possibile "completare" con tali slot un calendario digitale, lasciando spazi liberi e quindi prenotabili per nuovi Driving Slot.
Si otterrà così un sistema estremamente semplice e intuitivo nel suo utilizzo.

Un ulteriore aspetto non tenuto in considerazione durante la progettazione e lo sviluppo (per mancanza di risorse temporali e non), è l'autenticazione delle Routes e l'eventuale aggiunta di un Gateway. Questo aumenterebbe non poco la sicurezza del sistema in uso e consentirebbe di esporre all'utente finale le sole Routes a lui indirizzate (non visualizzando routes infra-servizi).

Inoltre, determinato codice e classi di utilità sono duplicate tra microservizi (scelta effettuata per mantenere separazione tra essi). Sarebbe possibile inserire una libreria intermedia, che renda disponibile in modo comune tale codice ai microservizi che ne richiedono l'utilizzo, riducendo duplicazione e mantenendo semplicità nel codice utilizzato.
