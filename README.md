# Xformer Kettle Server

# Descrizione

Xformer Kettle Server è un'applicazione web per servlet container che funge da wrapper alle librerie di [Data Integration di Penthao Kettle](https://github.com/pentaho/pentaho-kettle). L'applicazione espone dei webservice SOAP e REST per l'inserimento, l'amministrazione e l'esecuzione di trasformazioni modellate tramite l'applicativo [Kettle Spoon](https://www.hitachivantara.com/es-latam/products/pentaho-platform/data-integration-analytics/pentaho-community-edition.html).

# Installazione

Requisiti minimi per installazione:

- Sistema operativo : Linux server.
- Java versione 11 (OpenJDK / Oracle).
- Tomcat versione 9;
- MySQL/MariaDB/Oracle DB (versione consigliata 19c).

## Installazione JDK
Il software richiede OpenJDK alla versione 11, guida all'installazione [qui](https://openjdk.org/install/).

## Setup Tomcat 9
Il software richiede Apache Tomcat alla versione 9, quida all'installazione [qui](https://tomcat.apache.org/tomcat-9.0-doc/setup.html).

## Predisposizione Database
Con il software viene fornito lo script [kettleserver_boilerplate.sql](src/docs/kettleserverrepositoryboilerplate.sql), per popolare lo schema che verrà utilizzato durante l'esecuzione come repository.

# Utilizzo

I servizi REST esposti sono i seguenti:

- **versione** : resituisce il numero di versione corrente del software.
- **trasformazioni** : resituisce lo stato delle trasformazioni caricatate.

I servizi SOAP esposti sono i seguenti:

- **esistenzaCartella** : controlla l'esistenza di una determinata cartella nel repository di kettle.
- **inserisciTransformation** : aggiunge una nuova trasformazione nel repository kettle.
- **statusCodaTrasformazione** : resituisce un immagine delle trasformazione in corso, in coda o eseguite.
- **inserisciJob** : aggiunge un nuovo job nel repository kettle.
- **eseguiTrasformazione** : esegue una trasformazione presente nel repository.
- **inserisciCartella** : crea una nuova cartella nel repository di kettle.
- **ottieniParametri** : ottiene una lista dei parametri di una determinata trasformazione.
- **eliminaCartella** : elimina una cartella nel repository di kettle.

[Qui](src/docs/kettleserver.wsdl) il wsdl dell'endpoint SOAP.

# Supporto

Mantainer del progetto è [Engineering Ingegneria Informatica S.p.A.](https://www.eng.it/).

# Contributi

Se interessati a crontribuire alla crescita del progetto potete scrivere all'indirizzo email <a href="mailto:areasviluppoparer@regione.emilia-romagna.it">areasviluppoparer@regione.emilia-romagna.it</a>.

# Credits

Progetto di proprietà di [Regione Emilia-Romagna](https://www.regione.emilia-romagna.it/) sviluppato a cura di [Engineering Ingegneria Informatica S.p.A.](https://www.eng.it/).

# Licenza

Questo progetto è rilasciato sotto licenza GNU Affero General Public License v3.0 or later ([LICENSE.txt](LICENSE.txt)).
