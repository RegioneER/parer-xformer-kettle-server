# Flusso `eseguiTrasformazione`

Questo documento descrive il workflow completo della chiamata SOAP `eseguiTrasformazione`, distinguendo la parte sincrona (risposta al chiamante) dalla parte asincrona (esecuzione reale del job Kettle).

## Vista sequenziale end-to-end

```mermaid
sequenceDiagram
    autonumber
    participant C as Client SOAP
    participant S as TrasformazioniSoapServiceImpl
    participant G as GestoreTrasformazioniImpl
    participant D as DataServiceImpl
    participant R as Repository Kettle
    participant J as Job Kettle
    participant L as KettleCustomFileLoggingEventListener
    participant W as WS NotificaOggettoTrasformato

    C->>S: eseguiTrasformazione(idOggetto, nomeTrasformazione, parametri)
    S->>S: crea oggetto Trasformazione
    S->>G: possoEseguireTrasformazione(trasformazione)
    G->>D: accettaTrasformazione(trasformazione)

    alt Coda piena o id gia in coda/in corso
        D-->>G: false
        G-->>S: false
        S-->>C: Esito CODA_PIENA
    else Accettata
        D-->>G: true (stato = IN_CODA_TRASFORMAZIONE)
        G-->>S: true
        S->>G: eseguiTrasformazione(trasformazione) [@Async]
        S-->>C: Esito OK (accodata)

        Note over G: Da qui in poi flusso asincrono su threadPoolTaskExecutor

        G->>D: iniziaTrasformazione(trasformazione)
        D-->>G: stato = TRASFORMAZIONE_IN_CORSO + init report + idTrasfReport

        G->>G: controlla parametri Object Storage
        alt Parametri OS presenti
            G->>G: preparaTrasformazioneDaObjectStorage()
            Note over G: Scarica file S3 su disco locale\nAggiorna XF_INPUT_FILE_NAME\nRimuove parametri tecnici OS
        else Nessun Object Storage
            Note over G: Usa parametri cosi come ricevuti
        end

        G->>D: ottieni parametri config ws notifica
        G->>R: getRepository() + loadJob("main")
        G->>L: registra listener log per idTrasfReport
        G->>J: set parametri job + XF_REPORT_ID
        G->>J: start()
        G->>J: waitUntilFinished()

        alt job.getErrors() > 0
            G->>D: scartaTrasformazione(...)
            D-->>G: stato = ERRORE_TRASFORMAZIONE
        else job.getErrors() == 0
            G->>J: getResult().getExitStatus()
            G->>D: terminaTrasformazione(...)
            D-->>G: stato = TRASFORMAZIONE_TERMINATA
        end

        G->>D: generaReport(trasformazione)
        D-->>G: report XML

        G->>W: notificaOggettoTrasformato(idOggetto, errors/exitStatus, report)

        G->>J: stopAll() (if not null)
        G->>R: disconnect()
        G->>L: deregistra listener
        G->>G: cancella file locale OS (se presente)
        G->>D: pulisciReport(trasformazione)
    end
```

## Dettaglio stati monitoraggio

```mermaid
stateDiagram-v2
    [*] --> IN_CODA_TRASFORMAZIONE: accettaTrasformazione
    IN_CODA_TRASFORMAZIONE --> TRASFORMAZIONE_IN_CORSO: iniziaTrasformazione
    TRASFORMAZIONE_IN_CORSO --> TRASFORMAZIONE_TERMINATA: terminaTrasformazione
    TRASFORMAZIONE_IN_CORSO --> ERRORE_TRASFORMAZIONE: scartaTrasformazione
    IN_CODA_TRASFORMAZIONE --> ERRORE_TRASFORMAZIONE: errore in esecuzione async
```

## Note operative

- La risposta SOAP `OK` indica accodamento, non completamento della trasformazione.
- L'esecuzione reale avviene in async (`@Async("threadPoolTaskExecutor")`).
- In `finally`, il servizio prova sempre a rilasciare risorse e pulire report/log temporanei.
- Se il download da Object Storage e attivo, il file locale viene eliminato a fine flusso.

## Riferimenti codice

- `parer-kettle-soap/src/main/java/it/eng/parer/kettle/soap/TrasformazioniSoapServiceImpl.java`
- `parer-kettle-server/src/main/java/it/eng/parer/kettle/server/persistence/service/GestoreTrasformazioniImpl.java`
- `parer-kettle-server/src/main/java/it/eng/parer/kettle/server/persistence/service/DataServiceImpl.java`
- `parer-kettle-server/src/main/java/it/eng/parer/kettle/server/config/KettleBeanConfig.java`
- `parer-kettle-server/src/main/java/it/eng/parer/kettle/server/config/KettleCustomFileLoggingEventListener.java`

