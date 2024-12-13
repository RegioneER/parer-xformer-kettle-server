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

import it.eng.xformer.ws.NotificaOggettoTrasformato;
import it.eng.xformer.ws.NotificaOggettoTrasformato_Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Cappelli_F
 */
public class XformerWsClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(XformerWsClient.class);

    private static NotificaOggettoTrasformato_Service notificaOggettoTrasformatoService;

    private XformerWsClient() {
    }

    public static NotificaOggettoTrasformato getNotificaOggettoTrasformatoClient(String username, String password,
            String serviceURL, Integer timeout) {
        try {
            LOGGER.info("Connessione al WS: {}", serviceURL);
            synchronized (XformerWsClient.class) {
                if (notificaOggettoTrasformatoService == null) {
                    URL wsdlURL = new URL(serviceURL + "?wsdl");
                    notificaOggettoTrasformatoService = new NotificaOggettoTrasformato_Service(wsdlURL);
                    notificaOggettoTrasformatoService.setHandlerResolver(new SOAPClientLoginHandlerResolver());
                    LOGGER.debug("Creato il client service per il WS: {}", serviceURL);
                }
            }

            NotificaOggettoTrasformato client = notificaOggettoTrasformatoService.getNotificaOggettoTrasformatoPort();
            Map<String, Object> requestContext = ((BindingProvider) client).getRequestContext();
            // Timeout in millis
            requestContext.put("com.sun.xml.internal.ws.connect.timeout", timeout);
            // Timeout in millis
            requestContext.put("com.sun.xml.internal.ws.request.timeout", timeout);
            // Timeout in millis
            requestContext.put("com.sun.xml.ws.request.timeout", timeout);
            // Timeout in millis
            requestContext.put("com.sun.xml.ws.connect.timeout", timeout);
            // Timeout in millis
            requestContext.put("javax.xml.ws.client.connectionTimeout", timeout);
            // Timeout in millis
            requestContext.put("javax.xml.ws.client.receiveTimeout", timeout);
            // Endpoint URL
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceURL);
            requestContext.put(SOAPClientLoginHandler.USER, username);
            requestContext.put(SOAPClientLoginHandler.PWD, password);

            return client;
        } catch (MalformedURLException e) {
            LOGGER.error("Errore durante l'inizializzazione del bean. WS Sever offline?", e);
            return null;
        }
    }
}
