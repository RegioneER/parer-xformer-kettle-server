/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */
package it.eng.parer.kettle.server.persistence.service;

import it.eng.parer.kettle.model.KettleCrudException;
import it.eng.parer.kettle.model.KettleJob;
import it.eng.parer.kettle.model.KettleTransformation;
import it.eng.parer.kettle.model.Parametro;
import it.eng.parer.kettle.model.Trasformazione;
import it.eng.parer.kettle.server.Constants;
import it.eng.parer.kettle.server.config.KettleCustomFileLoggingEventListener;
import it.eng.parer.kettle.service.DataService;
import it.eng.parer.kettle.service.GestoreTrasformazioni;
import it.eng.parer.kettle.ws.client.AwsClient;
import it.eng.parer.kettle.ws.client.XformerWsClient;
import it.eng.xformer.ws.NotificaOggettoTrasformato;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import javax.activation.DataHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.trans.TransMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

/**
 *
 * @author Cappelli_F
 */
@Service
@Transactional
public class GestoreTrasformazioniImpl implements GestoreTrasformazioni {

    private static final Logger LOGGER = LoggerFactory.getLogger(GestoreTrasformazioniImpl.class);

    private DataService dataService;

    @Autowired
    private Environment env;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private AwsClient s3Clients;

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    private DatabaseMeta getDataBaseMeta() {
        String repoJNDI = env.getProperty("jndi.kettle_ds");

        DatabaseMeta connection = new DatabaseMeta();
        connection.setAccessType(DatabaseMeta.TYPE_ACCESS_JNDI);
        connection.setDBName(repoJNDI);
        connection.setName(repoJNDI);

        return connection;
    }

    private Repository getRepository() throws KettleException {

        Repository repository = null;
        try {
            String repoName = dataService.ottieniParametroConfigurazione("config.repo_name");
            String repoDesc = dataService.ottieniParametroConfigurazione("config.repo_desc");
            String repoUsername = dataService.ottieniParametroConfigurazione("config.repo_user");
            String repoPassword = dataService.ottieniParametroConfigurazione("config.repo_pwd");

            KettleDatabaseRepositoryMeta repositoryMeta = new KettleDatabaseRepositoryMeta();
            repositoryMeta.setName(repoName);
            repositoryMeta.setDescription(repoDesc);
            repositoryMeta.setConnection(getDataBaseMeta());

            PluginRegistry registry = PluginRegistry.getInstance();
            repository = registry.loadClass(RepositoryPluginType.class, repositoryMeta, Repository.class);
            repository.init(repositoryMeta);
            repository.connect(repoUsername, repoPassword);
        } catch (Exception ex) {
            LOGGER.error("Errore GRAVE durante la creazione del repository", ex);
            throw new KettleException(ex.getMessage(), ex);
        }

        return repository;
    }

    @Override
    public void inserisciJob(KettleJob job) throws KettleCrudException {
        DataHandler jobDescriptor = job.getJobDescriptor();
        Repository repository = null;
        File jobXmlFile = null;
        try {

            jobXmlFile = File.createTempFile("kettle_job_", ".kjb");

            try (FileOutputStream fio = new FileOutputStream(jobXmlFile);) {
                jobDescriptor.writeTo(fio);

                repository = getRepository();
                JobMeta jobMeta = new JobMeta(jobXmlFile.getCanonicalPath(), repository, null);
                repository.save(jobMeta, job.getVersione(), new GregorianCalendar(), null, true);
            }

        } catch (IOException | KettleException ex) {
            FileUtils.deleteQuietly(jobXmlFile);
            LOGGER.error("Errore durante l'inserimento di un job nel repository: " + ex.getMessage(), ex);
            throw new KettleCrudException("Errore durante l'inserimento del job nel repository.");
        } finally {

            if (repository != null) {
                repository.disconnect();
            }
        }

    }

    @Override
    public void inserisciTransformation(KettleTransformation transformation) throws KettleCrudException {
        DataHandler transformationDescriptor = transformation.getTransformationDescriptor();
        Repository repository = null;
        File transformationXmlFile = null;
        try {
            transformationXmlFile = File.createTempFile("kettle_trasformation_", ".kjt");
            try (FileOutputStream fio = new FileOutputStream(transformationXmlFile);) {
                transformationDescriptor.writeTo(fio);

                repository = getRepository();
                TransMeta transformationMeta = new TransMeta(transformationXmlFile.getCanonicalPath(), repository, true,
                        null, null);
                repository.save(transformationMeta, transformation.getVersione(), new GregorianCalendar(), null, true);
            }
        } catch (IOException | KettleException ex) {
            FileUtils.deleteQuietly(transformationXmlFile);
            LOGGER.error("Errore durante l'inserimento della transformation kettle: " + ex.getMessage(), ex);
            throw new KettleCrudException(
                    "Errore durante l'inserimento della transformation kettle: " + ex.getMessage(), ex);
        } finally {

            if (repository != null) {
                repository.disconnect();
            }
        }
    }

    @Override
    public void inserisciCartella(String nomeCartella) throws KettleCrudException {
        Repository repository = null;
        try {
            repository = getRepository();
            RepositoryDirectoryInterface rootDirectory = repository.loadRepositoryDirectoryTree();
            repository.createRepositoryDirectory(rootDirectory, nomeCartella);
        } catch (KettleException ex) {
            LOGGER.error("Errore durante l'inserimento della cartella {} ", nomeCartella, ex);
            throw new KettleCrudException("Errore durante  l'inserimento della cartella " + nomeCartella, ex);
        } finally {
            if (repository != null) {
                repository.disconnect();
            }
        }
    }

    @Override
    public void eliminaCartella(String nomeCartella) throws KettleCrudException {
        Repository repository = null;
        try {
            repository = getRepository();
            RepositoryDirectoryInterface rootDirectory = repository.loadRepositoryDirectoryTree();
            RepositoryDirectoryInterface transformationRootDirectory = rootDirectory.findDirectory(nomeCartella);
            if (transformationRootDirectory != null) {
                repository.deleteRepositoryDirectory(transformationRootDirectory);
            }
        } catch (KettleException ex) {
            LOGGER.error("Errore durante  l'eliminazione della cartella {} ", nomeCartella, ex);
            throw new KettleCrudException("Errore durante  l'eliminazione della cartella " + nomeCartella, ex);
        } finally {
            if (repository != null) {
                repository.disconnect();
            }
        }
    }

    @Override
    public List<Parametro> recuperaParametri(String nomeTrasformazione) {
        Repository repository = null;
        try {
            repository = getRepository();
            RepositoryDirectoryInterface repositoryRoot = repository.loadRepositoryDirectoryTree();
            RepositoryDirectoryInterface directory = repositoryRoot.findDirectory(nomeTrasformazione);

            JobMeta jobMeta = repository.loadJob("main", directory, null, null);

            String[] params = jobMeta.listParameters();

            List<Parametro> elencoParametri = new ArrayList<>(params.length);
            for (int i = 0; i < params.length; i++) {
                elencoParametri.add(new Parametro(params[i], jobMeta.getParameterDefault(params[i])));
            }

            return elencoParametri;

        } catch (Exception ex) {
            LOGGER.error("Errore durante il recupero dei parametri della trasformazione: " + ex.getMessage(), ex);
            throw new KettleCrudException(
                    "Errore durante il recupero dei parametri della trasformazione: " + ex.getMessage(), ex);
        } finally {
            if (repository != null) {
                repository.disconnect();
            }
        }
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void eseguiTrasformazione(Trasformazione trasformazione) {
        Repository repository = null;
        Job job = null;
        KettleCustomFileLoggingEventListener logListener = null;
        String objectStorageFilePath = "";
        boolean isObjectStorage = false;

        String transformationName = trasformazione.getNomeTrasformazione();
        List<Parametro> parameters = trasformazione.getParametri();

        try {
            dataService.iniziaTrasformazione(trasformazione);
            LOGGER.info("Avvio della trasformazione " + transformationName + " su oggetto "
                    + trasformazione.getIdOggettoPing() + ".");

            // MEV 22001 - controlliamo se tra i paramentri ci sono le informazioni per il recupero da Object Storage
            isObjectStorage = controllaSeTrasformazioneDaObjectStorage(parameters);
            if (isObjectStorage) {
                // MEV 22001 nel caso, mettiamo il file temporaneamente su disco, lo cancelleremo alla fine.
                objectStorageFilePath = preparaTrasformazioneDaObjectStorage(trasformazione);
            }

            String username = dataService.ottieniParametroConfigurazione("config.user_ws_notifica");
            String password = dataService.ottieniParametroConfigurazione("config.psw_ws_notifica");
            String serviceURL = dataService.ottieniParametroConfigurazione("config.url_ws_notifica");
            String soapTimeoutString = dataService.ottieniParametroConfigurazione("config.timeout_ws_notifica");
            Integer soapTimeout = Integer.valueOf(soapTimeoutString);

            repository = getRepository();
            RepositoryDirectoryInterface repositoryRoot = repository.loadRepositoryDirectoryTree();
            RepositoryDirectoryInterface directory = repositoryRoot.findDirectory(transformationName);

            logListener = appContext.getBean(KettleCustomFileLoggingEventListener.class);
            logListener.setIdTrasf(trasformazione.getIdTrasfReport());
            KettleLogStore.getAppender().addLoggingEventListener(logListener);

            JobMeta jobMeta = repository.loadJob("main", directory, null, null);

            job = new Job(repository, jobMeta, null);

            for (Parametro parameter : parameters) {
                // MEV 22001 - se abbiamo computato un nuovo percorso per il file da trasformare
                // aggiorniamo il parametro.
                if (isObjectStorage && parameter.getNomeParametro().equals(Constants.XF_INPUT_FILE_NAME)) {
                    job.getJobMeta().setParameterValue(parameter.getNomeParametro(), objectStorageFilePath);
                } else {
                    job.getJobMeta().setParameterValue(parameter.getNomeParametro(), parameter.getValoreParametro());
                }
            }

            job.getJobMeta().setParameterValue("XF_REPORT_ID", String.valueOf(trasformazione.getIdTrasfReport()));

            job.start();
            job.waitUntilFinished();

            LOGGER.info("Fine della trasformazione " + transformationName + " su oggetto "
                    + trasformazione.getIdOggettoPing() + ".");

            int errors = job.getErrors();

            if (errors > 0) {
                dataService.scartaTrasformazione(trasformazione, "");
            } else {
                // se gli errori sono zero controlliamo che lo stato d'uscita non sia in warning (-1)
                errors = job.getResult().getExitStatus();
                dataService.terminaTrasformazione(trasformazione);
            }

            String report = dataService.generaReport(trasformazione);

            LOGGER.info(
                    "Inizio di notifica trasformazione oggetto " + trasformazione.getIdOggettoPing() + " completata.");
            NotificaOggettoTrasformato client = XformerWsClient.getNotificaOggettoTrasformatoClient(username, password,
                    serviceURL, soapTimeout);
            // Visto che notificaOggettoTrasformato è internamente un metodo asincrono che ritorna void la risposta sarà
            // sempre OK
            client.notificaOggettoTrasformato(trasformazione.getIdOggettoPing(), errors, report);

        } catch (KettleException ex) {
            dataService.scartaTrasformazione(trasformazione, ex.getMessage());

            LOGGER.error("Errore durante l'esecuzione della trasformazione su oggetto "
                    + trasformazione.getIdOggettoPing() + ": " + ex.getMessage(), ex);
            throw new KettleCrudException("Errore durante l'esecuzione della trasformazione: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            dataService.scartaTrasformazione(trasformazione, ex.getMessage());

            LOGGER.error("Errore non gestito durante l'esecuzione della trasformazione", ex);
            throw new KettleCrudException("Errore non gestito durante l'esecuzione della trasformazione", ex);
        } finally {
            try {
                if (job != null) {
                    job.stopAll();
                }
                if (repository != null) {
                    repository.disconnect();
                }

                if (logListener != null) {
                    KettleLogStore.getAppender().removeLoggingEventListener(logListener);
                }

                // se il file veniva da object storage ora bisogna rimuoverlo da disco
                if (isObjectStorage && !objectStorageFilePath.isEmpty()) {
                    File objectStorageFile = new File(objectStorageFilePath);
                    FileUtils.deleteQuietly(objectStorageFile);
                }

                dataService.pulisciReport(trasformazione);
            } catch (Exception ex) {
                throw new KettleCrudException("Errore durante la chiusura della trasformazione: " + ex.getMessage(),
                        ex);
            }
        }
    }

    @Override
    public boolean possoEseguireTrasformazione(Trasformazione trasformazione) {
        boolean result = dataService.accettaTrasformazione(trasformazione);

        LOGGER.info(
                "Posso eseguire la trasformazione sull'oggetto " + trasformazione.getIdOggettoPing() + "? " + result);

        // controlliamo solo la parte di object storage
        return result;
    }

    @Override
    public boolean esistenzaCartella(String nomeCartella) {
        Repository repository = null;
        boolean result = false;

        try {
            repository = getRepository();
            RepositoryDirectoryInterface repositoryRoot = repository.loadRepositoryDirectoryTree();
            RepositoryDirectoryInterface directory = repositoryRoot.findDirectory(nomeCartella);

            if (directory != null) {
                result = true;
            }

        } catch (KettleException ex) {
            java.util.logging.Logger.getLogger(GestoreTrasformazioniImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (repository != null) {
                    repository.disconnect();
                }
            } catch (Exception ex) {
                throw new KettleCrudException("Errore durante il controllo di esistenza della cartella \""
                        + nomeCartella + "\": " + ex.getMessage(), ex);
            }
        }

        return result;
    }

    private boolean controllaSeTrasformazioneDaObjectStorage(List<Parametro> parameters) throws KettleException {
        boolean POSKFound = false;
        boolean POSBFound = false;
        boolean POSUFound = false;
        boolean POSUSFound = false;
        boolean POSPFound = false;

        for (Parametro parameter : parameters) {
            if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_KEY)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                POSKFound = true;
            }

            if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_BUCKET)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                POSBFound = true;
            }

            if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_URL)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                POSUFound = true;
            }

            if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_USER)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                POSUSFound = true;
            }

            if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_PASSWORD)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                POSPFound = true;
            }
        }

        return POSKFound && POSBFound && POSUFound && POSUSFound && POSPFound;
    }

    private String preparaTrasformazioneDaObjectStorage(Trasformazione trasformazione)
            throws KettleException, URISyntaxException {
        String oSBucket = "";
        String oSKey = "";
        String oSUrl = "";
        String oSAccessId = "";
        String oSSecretKey = "";
        String targetFileDest = "";
        String targetFileDestDirectory = "";

        LOGGER.info("Inizio copia pacchetto da object storage per " + trasformazione.getIdOggettoPing());

        ListIterator<Parametro> iter = trasformazione.getParametri().listIterator();
        while (iter.hasNext()) {
            Parametro parameter = iter.next();

            if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_KEY)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                oSKey = parameter.getValoreParametro().trim();
                iter.remove(); // non passarlo alla trasformazione
            } else if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_BUCKET)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                oSBucket = parameter.getValoreParametro().trim();
                iter.remove(); // non passarlo alla trasformazione
            } else if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_URL)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                oSUrl = parameter.getValoreParametro().trim();
                iter.remove(); // non passarlo alla trasformazione
            } else if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_USER)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                oSAccessId = parameter.getValoreParametro().trim();
                iter.remove(); // non passarlo alla trasformazione
            } else if (parameter.getNomeParametro().equals(Constants.XF_OBJECT_STORAGE_PASSWORD)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                oSSecretKey = parameter.getValoreParametro().trim();
                iter.remove(); // non passarlo alla trasformazione
            } else if (parameter.getNomeParametro().equals(Constants.XF_INPUT_FILE_NAME)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                targetFileDest = parameter.getValoreParametro().trim();
            } else if (parameter.getNomeParametro().equals(Constants.XF_TMP_DIR)
                    && !parameter.getValoreParametro().trim().isEmpty()) {
                targetFileDestDirectory = parameter.getValoreParametro().trim();
            }
        }

        // TODO : controllare che siano effettivamete rimossi...
        if (oSBucket.isEmpty() || oSKey.isEmpty() || targetFileDest.isEmpty()) {
            throw new KettleException("Parametri per il recupero da object storage non configurati correttamente per "
                    + trasformazione.getIdOggettoPing());
        }

        S3Client s3SourceClient = s3Clients.getClient(new URI(oSUrl), oSAccessId, oSSecretKey);

        File targetFileDirectory = new File(targetFileDestDirectory + File.separator + "INPUT_FILE",
                FilenameUtils.getBaseName(targetFileDest));
        File targetFile = new File(targetFileDirectory, FilenameUtils.getName(targetFileDest));

        if (!targetFileDirectory.exists()) {
            targetFileDirectory.mkdirs();
        }

        try (OutputStream os = new FileOutputStream(targetFile)) {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(oSBucket).key(oSKey).build();
            ResponseInputStream<GetObjectResponse> s3Object = s3SourceClient.getObject(getObjectRequest);
            IOUtils.copyLarge(s3Object, os);

        } catch (Exception ex) {
            LOGGER.error("Errore nel recupero del file da object storage per " + trasformazione.getIdOggettoPing(), ex);
            throw new KettleException(
                    "Errore nel recupero del file da object storage per " + trasformazione.getIdOggettoPing());
        }

        LOGGER.info("Fine copia pacchetto da object storage per " + trasformazione.getIdOggettoPing());

        return targetFile.getAbsolutePath();
    }

}
