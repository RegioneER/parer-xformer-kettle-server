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

package it.eng.parer.kettle.soap;

import it.eng.parer.kettle.model.AbstractEsito;
import it.eng.parer.kettle.model.Esito;
import it.eng.parer.kettle.model.EsitoCartella;
import it.eng.parer.kettle.model.EsitoEsitenzaCartella;
import it.eng.parer.kettle.model.EsitoJob;
import it.eng.parer.kettle.model.EsitoListaParametri;
import it.eng.parer.kettle.model.EsitoStatusCodaTrasformazione;
import it.eng.parer.kettle.model.EsitoTransformation;
import it.eng.parer.kettle.model.KettleCrudException;
import it.eng.parer.kettle.model.KettleJob;
import it.eng.parer.kettle.model.KettleTransformation;
import it.eng.parer.kettle.model.Parametro;
import it.eng.parer.kettle.model.StatoTrasformazione;
import it.eng.parer.kettle.model.Trasformazione;
import it.eng.parer.kettle.model.TrasformazioneException;
import it.eng.parer.kettle.service.DataService;
import it.eng.parer.kettle.service.GestoreTrasformazioni;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import it.eng.parer.kettle.soap.client.TrasformazioniSoapService;
import java.util.Date;

/**
 * Implementazione del WS che si occupa della gestione delle trasfromazioni.
 *
 * @author Snidero_L
 */
public class TrasformazioniSoapServiceImpl implements TrasformazioniSoapService {

    private static final Logger LOG = LoggerFactory.getLogger(TrasformazioniSoapServiceImpl.class);
    private static final String ERRORE_GENERICO_MSG = "Errore generico";
    private static final String ERRORE_NONGESTITO_MSG = "Errore non gestito";

    private GestoreTrasformazioni gestoreTrasformazioni;
    private DataService dataService;

    public void setGestoreTrasformazioni(GestoreTrasformazioni gestoreTrasformazioni) {
        this.gestoreTrasformazioni = gestoreTrasformazioni;
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public Esito eseguiTrasformazione(long idOggetto, String nomeTrasformazione, List<Parametro> parametri) {

        Trasformazione trasformazione = new Trasformazione();

        trasformazione.setIdOggettoPing(idOggetto);
        trasformazione.setParametri(parametri);
        trasformazione.setNomeTrasformazione(nomeTrasformazione);

        Esito esito = new Esito();
        esito.setNomeTrasformazione(nomeTrasformazione);
        try {
            if (gestoreTrasformazioni.possoEseguireTrasformazione(trasformazione)) {
                LOG.info("Trasformazione accodata per l'id oggetto " + idOggetto);

                gestoreTrasformazioni.eseguiTrasformazione(trasformazione);

                esito.setEsitoSintetico(Esito.ESITO_SINTETICO.OK);
                esito.setDettaglio("Trasformazione accodata per l'id oggetto " + idOggetto);
            } else {
                LOG.error("Coda del server kettle piena.");
                esito.setEsitoSintetico(Esito.ESITO_SINTETICO.CODA_PIENA);
                esito.setDettaglio("Coda del server kettle piena.");
            }

        } catch (TrasformazioneException e) {
            LOG.error("Trasformazione non eseguible", e);
            esito.setEsitoSintetico(Esito.ESITO_SINTETICO.KO);
            esito.setDettaglio(e.getMessage());
        } catch (Exception ex) {
            LOG.error(ERRORE_GENERICO_MSG, ex);
            esito.setEsitoSintetico(Esito.ESITO_SINTETICO.KO);
            esito.setDettaglio(ERRORE_NONGESTITO_MSG);
        }

        return esito;

    }

    @Override
    public EsitoJob inserisciJob(KettleJob kettleJob) {

        EsitoJob esito = new EsitoJob();
        try {
            gestoreTrasformazioni.inserisciJob(kettleJob);
            esito.setDettaglio("inserimento riuscito per job  con versione " + kettleJob.getVersione());
            esito.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.OK);
        } catch (KettleCrudException ex) {
            LOG.error("Impossibile inserire il job nel repository", ex);
            esito.setDettaglio(ex.getMessage());
            esito.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.KO);
        } catch (Exception ex) {
            LOG.error(ERRORE_GENERICO_MSG, ex);
            esito.setEsitoSintetico(Esito.ESITO_SINTETICO.KO);
            esito.setDettaglio(ERRORE_NONGESTITO_MSG);
        }
        return esito;
    }

    @Override
    public EsitoTransformation inserisciTransformation(KettleTransformation kettleTrasformation) {
        EsitoTransformation esito = new EsitoTransformation();
        try {
            gestoreTrasformazioni.inserisciTransformation(kettleTrasformation);
            esito.setDettaglio(
                    "inserimento riuscito per la transformation con versione " + kettleTrasformation.getVersione());
            esito.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.OK);
        } catch (KettleCrudException ex) {
            LOG.error("Impossibile inserire la transformation nel repository", ex);
            esito.setDettaglio(ex.getMessage());
            esito.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.KO);
        } catch (Exception ex) {
            LOG.error(ERRORE_GENERICO_MSG, ex);
            esito.setEsitoSintetico(Esito.ESITO_SINTETICO.KO);
            esito.setDettaglio(ERRORE_NONGESTITO_MSG);
        }
        return esito;
    }

    @Override
    public EsitoCartella inserisciCartella(String nomeCartella) {
        EsitoCartella esito = new EsitoCartella();
        try {
            gestoreTrasformazioni.inserisciCartella(nomeCartella);
            esito.setDettaglio("inserimento riuscito per la cartella con nome " + nomeCartella);
            esito.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.OK);
        } catch (KettleCrudException ex) {
            LOG.error("Impossibile inserire la cartella nel repository", ex);
            esito.setDettaglio(ex.getMessage());
            esito.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.KO);
        } catch (Exception ex) {
            LOG.error(ERRORE_GENERICO_MSG, ex);
            esito.setEsitoSintetico(Esito.ESITO_SINTETICO.KO);
            esito.setDettaglio(ERRORE_NONGESTITO_MSG);
        }
        return esito;
    }

    @Override
    public EsitoCartella eliminaCartella(String nomeCartella) {
        EsitoCartella esito = new EsitoCartella();
        try {
            gestoreTrasformazioni.eliminaCartella(nomeCartella);
            esito.setDettaglio("cancellazione riuscita per la cartella con nome " + nomeCartella);
            esito.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.OK);
        } catch (KettleCrudException ex) {
            LOG.error("Impossibile eliminare la cartella nel repository", ex);
            esito.setDettaglio(ex.getMessage());
            esito.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.KO);
        } catch (Exception ex) {
            LOG.error(ERRORE_GENERICO_MSG, ex);
            esito.setEsitoSintetico(Esito.ESITO_SINTETICO.KO);
            esito.setDettaglio(ERRORE_NONGESTITO_MSG);
        }
        return esito;
    }

    @Override
    public EsitoListaParametri ottieniParametri(String nomeTrasformazione) {
        EsitoListaParametri esito = new EsitoListaParametri();

        List<Parametro> parameters = gestoreTrasformazioni.recuperaParametri(nomeTrasformazione);
        esito.setParameters(parameters);

        return esito;
    }

    @Override
    public EsitoEsitenzaCartella esistenzaCartella(String nomeCartella) {
        EsitoEsitenzaCartella esitoEsitenzaCartella = new EsitoEsitenzaCartella();

        boolean esistenza = gestoreTrasformazioni.esistenzaCartella(nomeCartella);
        esitoEsitenzaCartella.setEsito(esistenza);

        return esitoEsitenzaCartella;
    }

    @Override
    public EsitoStatusCodaTrasformazione statusCodaTrasformazione(Date startDate, Date endDate, int numResults) {
        EsitoStatusCodaTrasformazione esct = new EsitoStatusCodaTrasformazione();

        List<StatoTrasformazione> trasformazioniInCorso = dataService.ottieniTrasformazioniAttive();
        esct.setTrasformazioniInCorso(trasformazioniInCorso);

        List<StatoTrasformazione> trasformazioniInCoda = dataService.ottieniTrasformazioniInCoda();
        esct.setTrasformazioniInCoda(trasformazioniInCoda);

        List<StatoTrasformazione> storicoTrasformazioni = dataService.getStoricoTrasformazioni(startDate, endDate,
                numResults);
        esct.setStoricoTrasformazioni(storicoTrasformazioni);

        esct.setEsitoSintetico(AbstractEsito.ESITO_SINTETICO.OK);

        return esct;
    }

}
