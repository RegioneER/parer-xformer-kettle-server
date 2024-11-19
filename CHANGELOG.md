
## 2.1.0 (11-11-2024)

### Bugfix: 2
- [#34198](https://parermine.regione.emilia-romagna.it/issues/34198) Lista di risultati troncati dalla chiamata allo storico delle trasformazioni
- [#34064](https://parermine.regione.emilia-romagna.it/issues/34064) Gestione del carattere + nel nome dell'oggetto

### Novità: 1
- [#34451](https://parermine.regione.emilia-romagna.it/issues/34451) Rimuovere il parametro XF_KETTLE_DB_PASSWORD dal report di trasformazione

### SUE: 1
- [#34063](https://parermine.regione.emilia-romagna.it/issues/34063) Modifica all'url di AWS nella configurazione di kettle server (tutti gli ambienti)

## 2.0.0 (24-04-2024)

### Bugfix: 1
- [#27975](https://parermine.regione.emilia-romagna.it/issues/27975) Correzione della gestione dell'avvio di kettle senza la presenza di object storage

### Novità: 3
- [#30870](https://parermine.regione.emilia-romagna.it/issues/30870) Migrazione plugin Kettle alla versione 9.4
- [#29136](https://parermine.regione.emilia-romagna.it/issues/29136) Migrazione Kettle alla versione 9.4
- [#25567](https://parermine.regione.emilia-romagna.it/issues/25567) Gestione esterna al pacchetto di deploy per le configurazioni di Kettle

## 1.1.1 (28-06-2021)

### Novità: 2
- [#25024](https://redmine.ente.regione.emr.it/issues/25024) Webservice per il monitoraggio della coda di Kettle Server
- [#22001](https://redmine.ente.regione.emr.it/issues/22001) Modifica modalità di recupero oggetto da trasformare da parte di Kettle Server

## 1.1.0

### Bugfix: 1
- [#24001](https://redmine.ente.regione.emr.it//issues/24001) Confiltto tra versioni di Bouncy Castle

### Novità: 1
- [#24002](https://redmine.ente.regione.emr.it//issues/24002) Predisporre le configurazioni di kettle server per ogni singolo ambiente

## 1.0.19

### Bugfix: 1
- [#23779](https://redmine.ente.regione.emr.it//issues/23779) L'applicativo genera una quantità di log anormale

### Novità: 1
- [#23966](https://redmine.ente.regione.emr.it//issues/23966) Aggiornare la xformer-transformations-utils-lib alla versione 0.0.8

## 1.0.18

### EVO: 1
- [#22107](https://redmine.ente.regione.emr.it//issues/22107) Modifiche a supporto della parallelizzazione di più istanze di Kettle Server

### Novità: 3
- [#22665](https://redmine.ente.regione.emr.it//issues/22665) Report trasformazioni: gestione dati xml strutturati nel report
- [#22632](https://redmine.ente.regione.emr.it//issues/22632) Gestione delle stato warning nelle trasformazioni
- [#22283](https://redmine.ente.regione.emr.it//issues/22283) Aggiornare il parer-pom alla versione corrente.

## 1.0.17.2

### Bugfix: 1
- [#23778](https://redmine.ente.regione.emr.it//issues/23778) L'applicativo genera una quantità di log anormale

## 1.0.17.1

### Bugfix: 1
- [#16169](https://redmine.ente.regione.emr.it//issues/16169) Migliorare leggibilità dei log delle trasformazioni

## 1.0.17

### Bugfix: 1
- [#22022](https://redmine.ente.regione.emr.it//issues/22022) Pulire i dati inseriti nel report dai caratteri non ammessi in un xml

### Novità: 1
- [#21695](https://redmine.ente.regione.emr.it//issues/21695) Modifiche a supporto dei plugin per la generazione del report

## 1.0.16 (07-02-2020)

### Bugfix: 2
- [#20900](https://redmine.ente.regione.emr.it//issues/20900) errata connessione datasource
- [#18092](https://redmine.ente.regione.emr.it//issues/18092) Inizializzazione tabella storico trasformazioni

## 1.0.15 (13-01-2020)

### Novità: 3
- [#20978](https://redmine.ente.regione.emr.it//issues/20978) Invio a PING del report della trasformazione
- [#19387](https://redmine.ente.regione.emr.it//issues/19387) Rimuovere la necessità di un servlet-container
- [#18916](https://redmine.ente.regione.emr.it//issues/18916) Gestire la trasformazione di più oggetti in parallelo

## 1.0.13

### Bugfix: 1
- [#18008](https://redmine.ente.regione.emr.it//issues/18008) Problema salvataggio Trasformazioni

## 1.0.12

### Novità: 1
- [#16754](https://redmine.ente.regione.emr.it//issues/16754) Gestione information leakage su kettle server 

## 1.0.11 (13-09-2018)

### Bugfix: 1
- [#16011](https://redmine.ente.regione.emr.it//issues/16011) Modifica al sistema di logging per la libreria p7mextractor

## 1.0.10 (05-09-2018)

### Bugfix: 1
- [#15917](https://redmine.ente.regione.emr.it//issues/15917) Libreria non presente in ambiente di test

### Novità: 1
- [#15955](https://redmine.ente.regione.emr.it//issues/15955) Centralizzazione dei log delle trasformazioni

## 1.0.9

### Novità: 1
- [#15850](https://redmine.ente.regione.emr.it//issues/15850) Parametrizzare la dimensione delle trasformazioni da eseguire in parallelo

## 1.0.6 (13-06-2018)

### EVO: 1
- [#14880](https://redmine.ente.regione.emr.it//issues/14880) Porting XFormer su Tomcat
