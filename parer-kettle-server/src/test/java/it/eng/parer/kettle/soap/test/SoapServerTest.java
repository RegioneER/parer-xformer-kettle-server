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

package it.eng.parer.kettle.soap.test;

import it.eng.parer.kettle.model.Esito;
import it.eng.parer.kettle.model.EsitoCartella;
import it.eng.parer.kettle.model.EsitoJob;
import it.eng.parer.kettle.model.EsitoListaParametri;
import it.eng.parer.kettle.model.EsitoTransformation;
import it.eng.parer.kettle.model.KettleJob;
import it.eng.parer.kettle.model.KettleTransformation;
import it.eng.parer.kettle.model.Parametro;
import it.eng.parer.kettle.server.config.CXFConfig;
import java.util.Arrays;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import it.eng.parer.kettle.soap.client.TrasformazioniSoapService;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class SoapServerTest {

    private TrasformazioniSoapService trasformazioniSoapService;

    @Before
    public void init() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(TrasformazioniSoapService.class);
        Map<String, Object> props = new HashMap<>();
        props.put("mtom-enabled", Boolean.TRUE);
        factory.setProperties(props);
        // TODO: parametrizzare l'indirizzo
        factory.setAddress("http://localhost:9090/kettle-server/services" + CXFConfig.SOAP_TRASFORMAZIONI);
        trasformazioniSoapService = (TrasformazioniSoapService) factory.create();
        // TODO: pulizia dati per effettuare il test

    }

    @Test
    public void testEseguiTrasformazione() throws Exception {
        Parametro[] parametri = { new Parametro("key1", "val1"), new Parametro("key2", "val2"),
                new Parametro("key3", "val3") };

        Esito esito = trasformazioniSoapService.eseguiTrasformazione(42L, "Trasformazione di test",
                Arrays.asList(parametri));
        Assert.assertEquals(esito.getEsitoSintetico(), Esito.ESITO_SINTETICO.OK);
    }

    @Test
    public void testInserisciJob() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("SimpleJob.kjb").getFile());
        DataSource source = new FileDataSource(file);

        KettleJob kettleJob = new KettleJob();
        kettleJob.setVersione("v.42");
        kettleJob.setJobDescriptor(new DataHandler(source));

        EsitoJob esito = trasformazioniSoapService.inserisciJob(kettleJob);
        Assert.assertEquals(esito.getEsitoSintetico(), Esito.ESITO_SINTETICO.OK);
    }

    @Test
    public void testInserisciTransformation() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("SimpleTransformation.ktr").getFile());
        DataSource source = new FileDataSource(file);

        KettleTransformation kettleTrans = new KettleTransformation();
        kettleTrans.setVersione("v.42");
        kettleTrans.setTransformationDescriptor(new DataHandler(source));

        EsitoTransformation esito = trasformazioniSoapService.inserisciTransformation(kettleTrans);
        Assert.assertEquals(esito.getEsitoSintetico(), Esito.ESITO_SINTETICO.OK);
    }

    @Test
    public void testInserisciCartella() throws Exception {
        EsitoCartella esito = trasformazioniSoapService.inserisciCartella("cartella_di_test");
        Assert.assertEquals(esito.getEsitoSintetico(), Esito.ESITO_SINTETICO.OK);
    }

    @Test
    public void testEliminaCartella() throws Exception {
        EsitoCartella esito = trasformazioniSoapService.eliminaCartella("cartella_di_test");
        Assert.assertEquals(esito.getEsitoSintetico(), Esito.ESITO_SINTETICO.OK);
    }

    @Test
    public void testOttieniParametri() throws Exception {
        EsitoListaParametri esito = trasformazioniSoapService.ottieniParametri("Trasformazione di test");
        List<Parametro> parametri = esito.getParameters();
        Assert.assertTrue(parametri.contains(new Parametro("key1", "val1")));
        Assert.assertTrue(parametri.contains(new Parametro("key2", "val2")));
        Assert.assertTrue(parametri.contains(new Parametro("key3", "val3")));
    }

}
