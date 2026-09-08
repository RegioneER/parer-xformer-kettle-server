# Elenco endpoint REST e SOAP

Riferimenti base:
- Base app: `/kettle-server`
- Base servlet CXF: `/services`

## Endpoint REST

Base REST: `/kettle-server/services/rest/v1`

| Metodo | Endpoint | Descrizione breve |
|:--|:--|:--|
| `GET` | `/kettle-server/services/rest/v1/versione` | Restituisce la versione del software (utile come check di disponibilita del servizio). |
| `GET` | `/kettle-server/services/rest/v1/trasformazioni` | Restituisce l'elenco delle trasformazioni attive/in corso. Supporta query param `status` (attualmente non usato come filtro nell'implementazione). |

## Endpoint SOAP

Endpoint SOAP (service address): `/kettle-server/services/soap/trasformazioni`  
WSDL: `/kettle-server/services/soap/trasformazioni?wsdl`

> Nel caso SOAP l'endpoint HTTP e uno solo; le operazioni sono invocate come metodi del servizio.

| Operazione SOAP | Descrizione breve |
|:--|:--|
| `eseguiTrasformazione` | Accoda l'esecuzione di una trasformazione Kettle per uno specifico oggetto. |
| `inserisciJob` | Carica un job Kettle (`.kjb`) nel repository. |
| `inserisciTransformation` | Carica una transformation Kettle (`.kjt`) nel repository. |
| `inserisciCartella` | Crea una cartella nel repository Kettle. |
| `eliminaCartella` | Elimina una cartella dal repository Kettle. |
| `ottieniParametri` | Recupera i parametri della trasformazione indicata. |
| `esistenzaCartella` | Verifica se una cartella esiste nel repository Kettle. |
| `statusCodaTrasformazione` | Restituisce lo stato della coda (in corso, in coda e storico). |
| `statusTrasformazione` | Restituisce l'ultimo stato noto di una trasformazione per `idObject`. |

## Sorgenti usate per l'estrazione

- `parer-kettle-server/src/main/java/it/eng/parer/kettle/server/KettleServerApplication.java`
- `parer-kettle-server/src/main/java/it/eng/parer/kettle/server/config/CXFConfig.java`
- `parer-kettle-rest-client/src/main/java/it/eng/parer/kettle/rest/client/TrasformazioniService.java`
- `parer-kettle-soap-client/src/main/java/it/eng/parer/kettle/soap/client/TrasformazioniSoapService.java`

