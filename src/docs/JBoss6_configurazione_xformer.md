---
title: "Configurazione Xformer"
---

# Configurazione Jboss EAP 6.4

## Versioni 

| Vers. doc | Vers. Xformer  | Modifiche  |
| -------- | ---------- | ---------- |
| 1.0.0    | 1.1.0 | Versione iniziale del documento  |

## Key Store 

È necessario mettere il keystore in formato JKS in una cartella accessibile all'IDP e poi configurare la system properties xformer-jks-path con il path al file.

## System properties

Dalla console web di amministrazione 

`Configuration > System properties`

impostare le seguenti properties

Chiave | Valore di esempio | Descrizione
--- | --- | ---
xformer-key-manager-pass | <password_jks_xformer> | Chiave del Java Key Store utilizzato per ottenere la chiave privata del circolo di fiducia dell’IDP.
xformer-timeout-metadata | 10000 | Timeout in secondi per la ricezione dei metadati dall’IDP.
xformer-temp-file | /var/tmp/tmp-xformer-federation.xml | Percorso assoluto del file xml che rappresenta l’applicazione all’interno del circolo di fiducia.
xformer-sp-identity-id | https://parer.regione.emilia-romagna.it/xformer | Identità dell’applicazione all’interno del circolo di fiducia.
xformer-refresh-check-interval | 600000 | Intervallo di tempo in secondi utilizzato per ricontattare l’IDP per eventuali variazioni sulla configurazione del circolo di fiducia.
xformer-jks-path | /opt/jboss-eap/certs/xformer.jks | Percorso assoluto del Java Key Store dell’applicazione.
xformer-store-key-name | xformer | Alias del certificato dell’applicazione all’interno del Java Key Store.
xf.aws.accessKeyId | <accessKeyId_object_storage> | Access Key id delle credenziali S3 per l’accesso all’object storage per il servizio di reportistica di Xformer.
xf.aws.secretKey | <secretKey_object_storage> | Secret Key delle credenziali S3 per l’accesso all’object storage per il servizio di reportistica di Xformer.
KETTLE_HOME | /opt/pentaho/7.0.0.0-25/kettle_home

```bash
/system-property=xformer-key-manager-pass:add(value="<password_jks_xformer>")
/system-property=xformer-timeout-metadata:add(value="10000")
/system-property=KETTLE_HOME:add(value="/opt/pentaho/7.0.0.0-25/kettle_home")
/system-property=xf.aws.accessKeyId:add(value="<accessKeyId_object_storage>")
/system-property=xf.aws.secretKey:add(value="<secretKey_object_storage>")
/system-property=xformer-jks-path:add(value="/opt/jboss-eap/certs/xformer.jks")
/system-property=xformer-refresh-check-interval:add(value="600000")
/system-property=xformer-store-key-name:add(value="xformer")
/system-property=xformer-temp-file:add(value="/var/tmp/tmp-xformer-federation.xml")
```

## Logging profile

```xml
<logging-profiles>
    <!-- ... -->
    <logging-profile name="XFORMER">
        <periodic-rotating-file-handler name="xformer_handler">
            <level name="INFO"/>
            <formatter>
                <pattern-formatter pattern="%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n"/>
            </formatter>
            <file relative-to="jboss.server.log.dir" path="xformer.log"/>
            <suffix value=".yyyy-MM-dd"/>
            <append value="true"/>
        </periodic-rotating-file-handler>
        <periodic-size-rotating-file-handler name="xformer_tx_connection_handler" autoflush="true">
            <level name="DEBUG"/>
            <formatter>
                <pattern-formatter pattern="%d{HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n"/>
            </formatter>
            <file relative-to="jboss.server.log.dir" path="xformer_conn_handler.log"/>
            <append value="true"/>
            <max-backup-index value="1">
            <rotate-size value="256m"/>
        </periodic-size-rotating-file-handler>

        <logger category="org.springframework" use-parent-handlers="true">
            <level name="ERROR"/>
        </logger>
        <logger category="org.opensaml" use-parent-handlers="true">
            <level name="ERROR"/>
        </logger>
        <logger category="org.exolab.castor.xml.NamespacesStack" use-parent-handlers="true">
            <level name="OFF"/>
        </logger>
        <logger category="org.exolab.castor.xml.EndElementProcessor" use-parent-handlers="true">
            <level name="ERROR"/>
        </logger>
        <logger category="it.eng.xformer" use-parent-handlers="true">
            <level name="INFO"/>
        </logger>
        <logger category="org.jboss.jca.core.connectionmanager.listener.TxConnectionListener" use-parent-handlers="true">
            <level name="DEBUG"/>
            <handlers>
                <handler name="xformer_tx_connection_handler"/>
            </handlers>
        </logger>
        <!-- ... -->
        <root-logger>
            <level name="INFO"/>
            <handlers>
                <handler name="xformer_handler"/>
            </handlers>
        </root-logger>
    </logging-profile>
    <!-- ... -->
</logging-profiles>
```

```bash
/subsystem=logging/logging-profile=XFORMER:add()
/subsystem=logging/logging-profile=XFORMER/periodic-rotating-file-handler=xformer_handler:add(level=INFO,formatter="%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n",file={path="xformer.log",relative-to="jboss.server.log.dir"},suffix=".yyyy-MM-dd",append=true)
/subsystem=logging/logging-profile=XFORMER/size-rotating-file-handler=xformer_tx_connection_handler:add(level=DEBUG,formatter="%d{yyyy-MM-dd HH:mm:ss,SSS} %-5p [%c] (%t) %s%E%n",file={path="xformer_conn_handler.log",relative-to="jboss.server.log.dir"},append=true,max-backup-index=1,rotate-size="256m")
/subsystem=logging/logging-profile=XFORMER/root-logger=ROOT:add(level=INFO,handlers=[xformer_handler])
/subsystem=logging/logging-profile=XFORMER/logger=org.springframework:add(level=ERROR,use-parent-handlers=true)
/subsystem=logging/logging-profile=XFORMER/logger=org.opensaml:add(level=ERROR,use-parent-handlers=true)
/subsystem=logging/logging-profile=XFORMER/logger=org.exolab.castor.xml.NamespacesStack:add(level=OFF,use-parent-handlers=true)
/subsystem=logging/logging-profile=XFORMER/logger=org.exolab.castor.xml.EndElementProcessor:add(level=ERROR,use-parent-handlers=true)
/subsystem=logging/logging-profile=XFORMER/logger=it.eng.xformer:add(level=INFO,use-parent-handlers=true)
/subsystem=logging/logging-profile=XFORMER/logger=org.jboss.jca.core.connectionmanager.listener.TxConnectionListener:add(level=DEBUG,handlers=[xformer_tx_connection_handler])
```
