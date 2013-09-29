camel-presentasjon
==================

Presentasjon av Apache Camel for internt fagseminar.

Hva er camel?
-------------

Integrasjon, integrasjon, integrasjon.

To ting:
1. hvordan håndtere flere applikasjoner og måtene de har å snakke med hverandre på
2. gode løsninger for integrasonsproblemer

Implementasjoner av Enterprise Integration Patterns (EIP).

Domene-spesifikt for EIP, uten et klart skille mellom deklarativ del og runtime.

Problemer
---------

PROBLEM 1:
Noe av det første vi ble oppmerksomme på var at all dokumentasjonen tok utgangspunkt i at alt kjørte i samme vm.

PROBLEM 2:
Men, men, dokumentasjonen sier jo at dette skal være enkelt? Hvor er transaksjonen min?

PROBLEM X:
De har laget et DSL, men det kan hende de ikke vet det selv. Ikke eksplisitt skille mellom den deklarative delen og runtimen.
Subproblem: oppsett oppfører seg forskjellig om man bruker xml eller kode for å sette opp ruter.
Subproblem: man møter på problemer som må debugges, og debugging er et helvete.

PROBLEM X:
Testing er et helvete.

PROBLEM X:
Testing er et helvete. Testene vi lagde i sted kan vi jo ikke stole på.

PROBLEM X:
Saker meldt som bugs blir løst som bugs, uten at det nødvendigvis er det. Brekker bakoverkompatibiltet på patchversjon.

PROBLEM X:
De små programmene våre er plutselig ikke så små lenger når vi drar inn tre mastodontrammeverk: spring, camel og activemq.
