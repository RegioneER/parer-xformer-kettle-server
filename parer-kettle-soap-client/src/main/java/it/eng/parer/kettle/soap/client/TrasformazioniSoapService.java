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

package it.eng.parer.kettle.soap.client;

import it.eng.parer.kettle.model.Esito;
import it.eng.parer.kettle.model.EsitoCartella;
import it.eng.parer.kettle.model.EsitoEsitenzaCartella;
import it.eng.parer.kettle.model.EsitoJob;
import it.eng.parer.kettle.model.EsitoListaParametri;
import it.eng.parer.kettle.model.EsitoStatusCodaTrasformazione;
import it.eng.parer.kettle.model.EsitoTransformation;
import it.eng.parer.kettle.model.KettleJob;
import it.eng.parer.kettle.model.KettleTransformation;
import it.eng.parer.kettle.model.Parametro;
import java.util.Date;
import java.util.List;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

/**
 * Servizio SOAP utilizzato per la gestioen delle <em>Trasformazioni</em>. Utilizza <em>MTOM</em>.
 *
 * @author Snidero_L
 */
@WebService(targetNamespace = "http://trasformazioni.parer.eng.it")
public interface TrasformazioniSoapService {

    /**
     * Metodo che innesca l'inizio della <em>Trasformazione</em>. Lo stato che il <tt>PIG_OBJECT</tt> deve assumere in
     * caso di esito positivo deve essere <tt>XFORMER_IN_CORSO</tt> .
     *
     * @param idOggetto
     *            id del <tt>PIG_OBJECT</tt> relativo alla <em>Trasformazione</em>.
     * @param nomeTrasformazione
     *            nome <em>univoco</em> della trasformazione
     * @param parametri
     *            della trasformazioni (definiti sul repository kettle)
     * 
     * @return Esito dell'avvenuto lancio della trasformazione
     */
    @WebResult(name = "Esito")
    Esito eseguiTrasformazione(
            @WebParam(name = "idOggetto") @XmlElement(nillable = false, required = true) long idOggetto,
            @WebParam(name = "nomeTrasformazione") @XmlElement(nillable = false, required = true) String nomeTrasformazione,
            @WebParam(name = "parametro") @XmlElement(required = true) List<Parametro> parametri);

    /**
     * Inserimento del job di kettle. Necessario utilizzare <em>MTOM</em> per trasferire il file.
     *
     * @param kettleJob
     *            parametro obbligatorio
     * 
     * @return Esito dell'inserimento
     */
    @WebResult(name = "EsitoJob")
    EsitoJob inserisciJob(@WebParam(name = "job") @XmlElement(nillable = false, required = true) KettleJob kettleJob);

    /**
     * Inserimento della trasformation di kettle. Necessario utilizzare <em>MTOM</em> per trasferire il file.
     *
     * @param kettleTrasformation
     *            parametro obbligatorio
     * 
     * @return Esito dell'inserimento
     */
    @WebResult(name = "EsitoTransformation")
    EsitoTransformation inserisciTransformation(
            @WebParam(name = "transformation") @XmlElement(nillable = false, required = true) KettleTransformation kettleTrasformation);

    /**
     * Inserisci la cartella (corrispondente al nome della <em>Trasformazione</em>).
     *
     * @param nomeCartella
     *            nome della cartella/trasformazione
     * 
     * @return Esito dell'inserimento
     */
    @WebResult(name = "EsitoCartella")
    EsitoCartella inserisciCartella(
            @WebParam(name = "nomeCartella") @XmlElement(nillable = false, required = true) String nomeCartella);

    /**
     * Elimina la cartella (corrispondente al nome della <em>Trasformazione</em>).
     *
     * @param nomeCartella
     *            nome della cartella/trasformazione
     * 
     * @return Esito della cancellazione
     */
    @WebResult(name = "EsitoCartella")
    EsitoCartella eliminaCartella(
            @WebParam(name = "nomeCartella") @XmlElement(nillable = false, required = true) String nomeCartella);

    /**
     * Ottieni la lista dei parametri della trasformazione.
     *
     * @param nomeTrasformazione
     *            nome della trasformazione/cartella.
     * 
     * @return Lista dei parametri associati alla trasformazione/cartella.
     */
    @WebResult(name = "EsitoListaParametri")
    EsitoListaParametri ottieniParametri(
            @WebParam(name = "nomeTrasformazione") @XmlElement(nillable = false, required = true) String nomeTrasformazione);

    /**
     * Ottieni la lista dei parametri della trasformazione.
     *
     * @param nomeCartella
     *            nome della trasformazione/cartella.
     * 
     * @return esitenza della cartella
     */
    @WebResult(name = "EsitoEsitenzaCartella")
    EsitoEsitenzaCartella esistenzaCartella(
            @WebParam(name = "nomeCartella") @XmlElement(nillable = false, required = true) String nomeCartella);

    /**
     * 
     * @param startDate
     * @param endDate
     * @param numResults
     * 
     * @return
     */
    @WebResult(name = "EsitoStatusCodaTrasformazione")
    EsitoStatusCodaTrasformazione statusCodaTrasformazione(Date startDate, Date endDate, int numResults);

}
