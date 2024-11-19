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

import it.eng.parer.kettle.service.DataService;
import java.io.File;
import java.net.URI;
import java.time.Duration;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

/**
 *
 * @author Cappelli_F
 */
@Component
public class S3ClientBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(S3ClientBean.class);

    @Autowired
    private DataService dataService;

    private S3Client awsClient;

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

            final AwsCredentialsProvider credProvider = StaticCredentialsProvider
                    .create(AwsBasicCredentials.create(accessKeyId, secretKey));

            awsClient = S3Client.builder().endpointOverride(URI.create(storageAddress)).region(Region.US_EAST_1)
                    .credentialsProvider(credProvider).forcePathStyle(true)
                    .httpClientBuilder(ApacheHttpClient.builder().maxConnections(100)
                            .connectionTimeout(Duration.ofMinutes(1L)).socketTimeout(Duration.ofMinutes(10L)))
                    .build();

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
            awsClient.close();
        }
    }

    public void deleteObject(String bucketName, String key) {
        DeleteObjectRequest delOb = DeleteObjectRequest.builder().bucket(bucketName).key(key).build();
        awsClient.deleteObject(delOb);
    }

    public ResponseInputStream<GetObjectResponse> getObject(String bucketName, String key) throws Exception {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder().bucket(bucketName).key(key).build();
            return awsClient.getObject(getObjectRequest);

        } catch (AwsServiceException | SdkClientException e) {
            LOGGER.error("impossibile ottenere dal bucket " + bucketName + " oggetto con chiave " + key, e);
            throw new Exception("impossibile ottenere dal bucket " + bucketName + " oggetto con chiave " + key, e);
        }
    }

    public boolean doesObjectExist(String bucketName, String key) {
        HeadObjectRequest objectRequest = HeadObjectRequest.builder().key(key).bucket(bucketName).build();

        try {
            awsClient.headObject(objectRequest);
            return true;

        } catch (S3Exception e) {
            return false;
        }
    }

    public void putObject(String bucketName, String nomeFilePacchetto, File file) {
        PutObjectRequest putOb = PutObjectRequest.builder().bucket(bucketName).key(nomeFilePacchetto).build();
        awsClient.putObject(putOb, RequestBody.fromFile(file));
    }

    public void putObject(String bucketName, String nomeFilePacchetto, String content) {
        PutObjectRequest putOb = PutObjectRequest.builder().bucket(bucketName).key(nomeFilePacchetto).build();
        awsClient.putObject(putOb, RequestBody.fromString(content));
    }

    public boolean isActive() {
        return isActiveFlag;
    }
}
