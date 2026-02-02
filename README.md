# Xformer Kettle Server

# Descrizione

Xformer Kettle Server è un'applicazione web per servlet container che funge da wrapper alle librerie di [Data Integration di Penthao Kettle](https://github.com/pentaho/pentaho-kettle). L'applicazione espone dei webservice SOAP e REST per l'inserimento, l'amministrazione e l'esecuzione di trasformazioni modellate tramite l'applicativo [Kettle Spoon](https://www.hitachivantara.com/es-latam/products/pentaho-platform/data-integration-analytics/pentaho-community-edition.html).

# Installazione

Requisiti minimi per installazione:

- Sistema operativo Linux 64bit;
- Almeno 2 CPU core dedicati;
- 8 GB RAM minimi;
- 150 MB di spazio disco per l'applicazione (non includente lo spazio necessario all'esecuzione delle trasformazioni);
- Java 11 (OpenJDK / Oracle);
- Apache Tomcat 9;
- Oracle DB (versione consigliata 19c);

## Installazione JDK

Il software richiede OpenJDK alla versione 11, guida all'installazione [qui](https://openjdk.org/install/).

## Setup Apache Tomcat

Il software richiede Apache Tomcat alla versione 9, quida all'installazione [qui](https://tomcat.apache.org/tomcat-9.0-doc/setup.html).
Dopo l'installazione sono richiesti anche i seguenti passi:

- Creazione della cartella per i file temporanei della trasformazione ***/opt/pentaho/staging_<nome_univoco_per_questa_istanza_di_kettle>*** con permessi di lettura e scrittura per l'utente Tomcat
- Creazione della cartella dei plugin ***/opt/pentaho/9.4.0.1-467/plugins/*** con permessi di lettura per l'utente Tomcat
- La copia dei plugin standard di Kettle versione 7.0.0.0-25 all'interno della cartella ***/opt/pentaho/9.4.0.1-467/plugins/***
- Aggiungere nella cartella ***lib*** del server Tomcat la libreria per Oracle DB ***ojdbc16.jar***
- Montare sul percorso ***/ftp/SACERPING_<AMBIENTE>*** l'omonima cartella esposta dai nodi del cluster Jboss
- Configurare l'export NFS verso i nodi del cluster JBOSS della cartella ***/opt/pentaho/staging_<nome_univoco_per_questa_istanza_di_kettle>***
- Montare sui nodi del cluster Jboss sul percorso ***/opt/pentaho/staging_<nome_univoco_per_questa_istanza_di_kettle>*** l'omonima cartella esposta dal Kettle Server
- E' necessario aggiungere le informazioni della nuova istanza al database di Preingest: inserire nella tabella ***PIG_KS_INSTANCE*** una nuova riga con i parametri della nuova istanza Kettle Server

| Parametro | Descrizione |
|:---|:---|
| NM_KS_INSTANCE | nome identificativo dell'istanza (es. PUG) |
| URL_KS_INSTANCE | url del wsdl di kettle server (es. http://<host_name>:8080/kettle-server/services/soap/trasformazioni) |
| DIR_KS_INSTANCE | /opt/pentaho/staging_<nome_univoco_per_questa_istanza_di_kettle> |

- Aggiungere al file setenv.sh di Apache Tomcat i seguenti campi:

```
# Plugin di kettle-server
REPORT_USER=<user>
REPORT_PWD=<password>
REPORT_URL=jdbc:h2:mem:kettlelite

CATALINA_OPTS="
-Dspring.config.additional-location=${CATALINA_BASE}/conf/kettle/ \
-Dspring.profiles.active=<nome del profilo del file di override> \
"
```
Sarà anche necessario compilare le seguenti sezioni nei file di configurazione di Tomcat:

```xml
<Resource auth="Container"
driverClassName="oracle.jdbc.OracleDriver"
maxIdle="10"
maxTotal="20"
maxWaitMillis="-1"
name="jdbc/KettleDs"
password="<password>"
type="javax.sql.DataSource"
url="jdbc:oracle:thin:@<HOSTNAME_DB>:1521/<service>"
username="SACER_KETTLE"
global="jdbc/KettleDs"
factory="org.apache.tomcat.jdbc.pool.DataSourceFactory" />

<Resource auth="Container"
driverClassName="org.h2.Driver"
maxIdle="10"
maxTotal="20"
maxWaitMillis="-1"
name="jdbc/KettleLiteDs"
password="<password>"
type="javax.sql.DataSource"
url="jdbc:h2:mem:kettlelite;DB_CLOSE_DELAY=-1"
username="<username>"
global="jdbc/KettleLiteDs"
factory="org.apache.tomcat.jdbc.pool.DataSourceFactory" />
```

## Predisposizione Database
Con il software viene fornito lo script [kettleserver_boilerplate.sql](src/docs/kettleserverrepositoryboilerplate.sql), per popolare lo schema che verrà utilizzato durante l'esecuzione come repository.


## Configurazione
Il software contiente due file di configurazione chiamati **application.yml** e **application-override.yml**, il primo contenente tutti i parametri della configurazione di Kettle Server, il secondo utilizzato per sovrascrivere i parametri specifici relativi all'ambiente d'installazione. Questo file andrà poi rinominato e spostato a seconda dei valori impostati nei parametri **spring.config.additional-location** e **spring.profiles.active** durante la fase di setup del server Apache Tomcat.
Segue l'elenco di tutti i parametri di configurazione:

| Nome | Valore di default | Descrizione | 
|:---|:---|:---|
| ***server.servlet.context-path*** | /kettle-server | Context path dell'applicazione |
| ***jndi.kettle_ds*** | java:comp/env/jdbc/KettleDs | Nome del datasource principale esposto da Tomcat |
| ***jndi.kettle_lite_ds*** | java:comp/env/jdbc/KettleLiteDs | Nome del datasource per la reportistica esposto da Tomcat |
| ***config.instance_name*** | STANDARD | Nome dell'istanza di kettle server, utilizzato da Preingest nella configurazione del tipo oggetto |
| ***config.repo_name***| Xformer repository | Nome del repository delle trasformazioni |
| ***config.repo_desc*** | Xformer repository description | Descrizione del repository delle trasformazioni |
| ***config.repo_user*** | admin | Utente del repository delle trasformazioni |
| ***config.repo_pwd*** | admin | Password del repository delle trasformazioni |
| ***config.url_ws_notifica*** | https://<host_name>/sacerping/NotificaOggettoTrasformato | Url del webservice esposto da Preingest per la notifica di trasformazione completata |
| ***config.user_ws_notifica*** | user | Utente del webservice esposto da Preingest per la notifica |
| ***config.psw_ws_notifica*** | password | Password del webservice esposto da Preingest per la notifica |
| ***config.timeout_ws_notifica*** | 600000 | Tempo di timeout in millisecondi del webservice esposto da Preingest per la notifica |
| ***config.plugin_folder_path*** | /opt/pentaho/9.4.0.1-467/plugins/ | Percorso dove sono installati I plugin di Kettle Server |
| ***config.object_storage.enabled*** **_*_**| true | Flag per attivare l'utilizzo di Object Storage se richiesto da Preingest|
| ***config.object_storage.url*** **_*_**| https://<host_os>:8082 | Url del server di Object Storage |
| ***config.object_storage.access_key*** **_*_**| accessKey | Utente del server di Object Storage |
| ***config.object_storage.secret*** **_*_**| secret | Password del server di Object Storage |
| ***transformation.concurrency*** | 3 | Numero di trasformazioni eseguite in parallelo |
| ***transformation.queue*** | 10 | Lunghezza della coda delle trasformazioni in attesa di esere eseguite |

***Nota***
 I parametri contrassegnati con **_*_** sono supportati **fino alla versione 2.x._*_ di Kettle inclusa**. A partire dalla versione **3.0.0**, il loro utilizzo non è più consentito, in quanto sono stati migrati in una tabella dedicata alla gestione dei diversi Object Storage.


## Test installazione
Per verificare che il software sia attivo navigare all'indirizzo ***http://<host_name>//kettle-server/services/rest/v1/versione***, Kettle Server risponderà con il suon numero di versione.


# Utilizzo

## Endpoint SOAP
Xformer Kettle Server espone i seguenti endpoint SOAP:

| Enpoint | Descrizione |
|:---|:---|
| ***/services/soap/trasformazioni/esistenzaCartella*** | Controlla l'esistenza di una determinata cartella nel repository di Kettle. |
| ***/services/soap/trasformazioni/inserisciCartella*** | Crea una nuova cartella nel repository di Kettle. |
| ***/services/soap/trasformazioni/eliminaCartella*** | Elimina una cartella nel repository di Kettle.|
| ***/services/soap/trasformazioni/inserisciTransformation*** | Aggiunge una nuova trasformazione nel repository kettle. |
| ***/services/soap/trasformazioni/inserisciJob*** | Aggiunge un nuovo job nel repository Kettle. |
| ***/services/soap/trasformazioni/ottieniParametri*** | Ottiene una lista dei parametri di una determinata trasformazione. |
| ***/services/soap/trasformazioni/eseguiTrasformazione*** | Esegue una trasformazione presente nel repository. |
| ***/services/soap/trasformazioni/statusCodaTrasformazione*** | Restituisce un'immagine della trasformazione in corso, in coda o eseguite. |

## Endpoint REST
Xformer Kettle Server espone i seguenti endpoint REST:

| Enpoint | Descrizione |
|:---|:---|
| ***/services/rest/v1/versione*** | Restituisce il numero di versione corrente del software.|
| ***/services/rest/v1/trasformazioni*** | Restituisce lo stato delle trasformazioni caricate.|

[Qui](src/docs/kettleserver.wsdl) il wsdl dell'endpoint SOAP.

# Supporto

Mantainer del progetto è [Engineering Ingegneria Informatica S.p.A.](https://www.eng.it/).

# Contributi

Se interessati a crontribuire alla crescita del progetto potete scrivere all'indirizzo email <a href="mailto:areasviluppoparer@regione.emilia-romagna.it">areasviluppoparer@regione.emilia-romagna.it</a>.

# Credits

Progetto di proprietà di [Regione Emilia-Romagna](https://www.regione.emilia-romagna.it/) sviluppato a cura di [Engineering Ingegneria Informatica S.p.A.](https://www.eng.it/).

# Licenza

Questo progetto è rilasciato sotto licenza GNU Affero General Public License v3.0 or later ([LICENSE.txt](LICENSE.txt)).
