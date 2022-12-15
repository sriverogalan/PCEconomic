# EconomicPC

![alt text](https://github.com/sriverogalan/ProjecteBotiga/blob/main/docs/logo.png?raw=true)

## Autors

- Miquel Angel Amengual Sastre
- Sergi Rivero Galan

## Descripció

ProjecteBotiga és un projecte de botiga online que permet gestionar els productes, les categories i les comandes.
La direcció de la botiga sera economicpc.me i el nom de la botiga sera EconomicPC. Tendrem una botiga online on es podran comprar productes relacionats amb la informàtica, com per exemple ordinadors, components, perifèrics, etc.

## Stakeholders:

- Administrador
- Usuari registrat
- Usuari públic

## Requisits funcionals:

- L’administrador ha de poder donar d’alta, baixa, modificar i llistar categories. Una categoria consta d’un nom i pot contenir subcategories (i aquestes també poden contenir altres subcategories.
- L’administrador ha de poder donar d’alta, baixa, modificar i llistar propietats d’articles. Les propietats consten d’un nom i un tipus (text, enumerat, número…). Per exemple: Talla pot ser un enumerat (S, XS, M, XL, XXL).
- L’administrador ha de poder donar d’alta, baixa, modificar i llistar articles de la tenda. Els articles poden tenir una o vàries propietats. Per exemple: una camiseta pot tenir color, talla, imatge…
- L’administrador ha de poder controlar l’stock de cada article. Aquest stock no només ha d’anar per article, sinó per article + propietats. Per exemple: hi pot haver 3 camisetes talla XS groc, 2 camisetes talla L groc i 5 camisetes talla XS verd.
- Els usuaris registrats han de poder marcar un article i un talla no disponible per a rebre una notificació quan aquest article estigui disponible.
- El sistema ha de tenir un algorisme de recomanacions personalitzat per usuaris registrats i usuaris públics.
- El sistema ha de tenir un càlcul de despeses d'enviament segons tamany o pes i segons el destí, tenint en compte destins internacionals (taula de tarifes d’un missatger)
- L’usuari públic i l’usuari registrat ha de poder consultar tots els articles disponibles de la botiga.
- L’usuari registrat pot comprar i realitzar el seguiment d’una comanda de la botiga. Cada compra ha de notificar per correu electrònic a l’usuari així com cada canvi d’estat del seguiment.
- L’administrador ha de poder importar la informació dels articles a partir d’un fitxer xml o csv. Aquest article serà proporcionat per els fabricants o marques. Si un article ja existeix s’ha d’actualitzar el seu preu, si aquest ha canviat.
- Els usuaris registrats han de tenir poder consultar les comandes anteriors.
- Els usuaris registrats han de poder pagar a través de Paypal i una pasarel·la de pagament d’un banc.
- L’administrador ha de poder treure un llistat en Excel dels aricles filtrats i ordenats per varis criteris del articles i propietats.
- Un usuari públic es pot registrar. Durant el procés de registre s’ha de validar l’adreça de correu.

## Requisits no funcionals:

- El sistema ha de ser segur.
- El sistema ha de tenir una bona usabilitat tan en desktop com amb mòbil.
