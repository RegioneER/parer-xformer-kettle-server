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

package it.eng.parer.kettle.ws.client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.UploadPartRequest;
import com.amazonaws.services.s3.model.UploadPartResult;
import it.eng.parer.kettle.service.DataService;
import java.io.File;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Cappelli_F
 */
@Component
public class S3ClientBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3ClientBean.class);

    @Autowired
    private DataService dataService;

    private AmazonS3 awsClient;

    private boolean isActiveFlag = false;

    @PostConstruct
    private void init() {
        // MAC27975 - se la property non è impostata o è diversa da "true"
        // le funzionalità dell'object storage vengono disattivate.
        final String isActiveSystemValue = dataService.ottieniParametroConfigurazione("config.object_storage.enabled");
        if (isActiveSystemValue != null) {
            isActiveFlag = Boolean.parseBoolean(isActiveSystemValue);
        }

        if (isActiveFlag) {
            String storageAddress = dataService.ottieniParametroConfigurazione("config.object_storage.url");
            // Leggo da configurazione le proprietà di sistema da cui ricevere le credenziali S3.
            String accessKeyId = dataService.ottieniParametroConfigurazione("config.object_storage.access_key");
            String secretKey = dataService.ottieniParametroConfigurazione("config.object_storage.secret");

            // Istanzio il client http (possiede le chiamate al protocollo Amazon S3)
            LOGGER.info("Sto per effettuare il collegamento all'endpoint S3 [ " + storageAddress + "]");
            BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKey);
            awsClient = AmazonS3Client.builder()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(storageAddress, Regions.US_EAST_1.name()))
                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                    .withPathStyleAccessEnabled(Boolean.TRUE).build();
            LOGGER.info("##########             CLIENT S3 INIZIALIZZATO              ###################");
        } else {
            LOGGER.info("##########             CLIENT S3 DISATTIVO              ###################");
        }
    }

    /*
     * Client Shutdown
     */
    @PreDestroy
    private void destroy() {
        LOGGER.info("Shutdown endpoint S3...");
        if (awsClient != null) {
            awsClient.shutdown();
        }
    }

    public void deleteObject(String bucketName, String key) {
        awsClient.deleteObject(bucketName, key);
    }

    public S3Object getObject(String bucketName, String key) {
        return awsClient.getObject(bucketName, key);
    }

    public boolean doesObjectExist(String bucketName, String key) {
        return awsClient.doesObjectExist(bucketName, key);
    }

    public void putObject(String bucketName, String nomeFilePacchetto, File file) {
        awsClient.putObject(bucketName, nomeFilePacchetto, file);
    }

    public void putObject(String bucketName, String nomeFilePacchetto, String content) {
        awsClient.putObject(bucketName, nomeFilePacchetto, content);
    }

    public InitiateMultipartUploadResult initiateMultipartUpload(
            InitiateMultipartUploadRequest initiateMultipartUploadRequest) {
        return awsClient.initiateMultipartUpload(initiateMultipartUploadRequest);
    }

    public CompleteMultipartUploadResult completeMultipartUpload(
            CompleteMultipartUploadRequest completeMultipartUploadRequest) {
        return awsClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

    public UploadPartResult uploadPart(UploadPartRequest uploadPartRequest) {
        return awsClient.uploadPart(uploadPartRequest);
    }

    public boolean isActive() {
        return isActiveFlag;
    }
}
