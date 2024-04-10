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

package it.eng.parer.kettle.server.config;

import javax.annotation.PostConstruct;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.Bus;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import it.eng.parer.kettle.service.GestoreTrasformazioni;

import it.eng.parer.kettle.rest.TrasformazioniServiceImpl;
import it.eng.parer.kettle.soap.TrasformazioniSoapServiceImpl;
import it.eng.parer.kettle.rest.client.TrasformazioniService;
import it.eng.parer.kettle.service.DataService;
import javax.xml.ws.Endpoint;
import org.apache.cxf.endpoint.Server;
import it.eng.parer.kettle.soap.client.TrasformazioniSoapService;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * Configurazione bean di spring relativi ai WS-SOAP e WS-REST.
 * 
 * @author Snidero_L
 */
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
public class CXFConfig {

    public static final String SOAP_TRASFORMAZIONI = "/soap/trasformazioni";
    public static final String REST_TRASFORMAZIONI_V1 = "/rest/v1";

    @Value("true")
    private boolean cxfDebug;

    @Value("true")
    private boolean mtomEnabled;

    @Value("${build.version}")
    private String versioneSoftware;

    @Autowired
    private Bus bus;

    @Autowired
    private GestoreTrasformazioni gestoreTrasformazioni;

    @Autowired
    private DataService dataService;

    @PostConstruct
    private void addLoggers() {
        if (cxfDebug) {
            LoggingInInterceptor loggingInInterceptor = new LoggingInInterceptor();
            bus.getInInterceptors().add(loggingInInterceptor);
            bus.getInFaultInterceptors().add(loggingInInterceptor);

            LoggingOutInterceptor loggingOutInterceptor = new LoggingOutInterceptor();
            bus.getOutInterceptors().add(loggingOutInterceptor);
            bus.getOutFaultInterceptors().add(loggingOutInterceptor);
        }
    }

    // --------------- SOAP
    @Bean
    public TrasformazioniSoapService soapTrasformazioniService() {
        TrasformazioniSoapServiceImpl service = new TrasformazioniSoapServiceImpl();
        service.setGestoreTrasformazioni(gestoreTrasformazioni);
        service.setDataService(dataService);
        return service;
    }

    private void enableMTOM(EndpointImpl endpoint) {
        SOAPBinding binding = (SOAPBinding) endpoint.getBinding();
        binding.setMTOMEnabled(mtomEnabled);
    }

    @Bean
    public Endpoint createSoapTrasformazioniEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, soapTrasformazioniService());
        endpoint.publish(SOAP_TRASFORMAZIONI);
        enableMTOM(endpoint);
        return endpoint;
    }

    // --------------- REST
    @Bean
    public TrasformazioniService restTrasformazioniService() {
        TrasformazioniServiceImpl service = new TrasformazioniServiceImpl();
        service.setGestoreTrasformazioni(gestoreTrasformazioni);
        service.setVersioneSoftware(versioneSoftware);
        return service;
    }

    @Bean
    public Server createTrasformazioniService() {
        JAXRSServerFactoryBean sfb = new JAXRSServerFactoryBean();
        sfb.setServiceBean(restTrasformazioniService());
        sfb.setAddress(REST_TRASFORMAZIONI_V1);
        sfb.setProvider(jacksonJsonProvider());
        return sfb.create();
    }

    @Bean
    public JacksonJsonProvider jacksonJsonProvider() {
        return new JacksonJsonProvider();
    }

}
