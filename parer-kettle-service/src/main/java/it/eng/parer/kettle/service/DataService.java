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

package it.eng.parer.kettle.service;

import it.eng.parer.kettle.model.StatoTrasformazione;
import it.eng.parer.kettle.model.Trasformazione;
import it.eng.parer.kettle.model.TrasformazioneException;
import java.util.Date;
import java.util.List;

/**
 * Service Layer. Tutti i metodi che agiscono sui dati dell'applicazione.
 *
 * @author Snidero_L
 */
public interface DataService {

    /**
     * Pulisce il db dalle netry sporche dopo un crash di sistema
     *
     */
    public void reinizializzaEPulisci();

    /**
     * Censisce la <em>Trasformazione</em> nella tabella di monitoraggio.
     *
     * @param trasformazione
     *            <em>Trasformazione</em> RER.
     * 
     * @throws TrasformazioneException
     *             nel caso in cui il pig object sia già stato da un'altra chiamata.
     */
    public boolean accettaTrasformazione(Trasformazione trasformazione) throws TrasformazioneException;

    /**
     * Imposta la data di inizio della trasformazione precedentemente inserita nella tabella di monitoraggio.
     *
     * @param trasformazione
     *            <em>Trasformazione</em> RER.
     * 
     * @throws TrasformazioneException
     *             nel caso in cui la Trasformazione non sia stata precedentemente censita.
     */
    public void iniziaTrasformazione(Trasformazione trasformazione) throws TrasformazioneException;

    /**
     * Imposta la data di termine della trasformazione precedentemente inserita nella tabella di monitoraggio.
     *
     * @param trasformazione
     *            <em>Trasformazione</em> RER.
     * 
     * @throws TrasformazioneException
     *             nel caso in cui la Trasformazione non sia stata precedentemente censita.
     */
    public void terminaTrasformazione(Trasformazione trasformazione) throws TrasformazioneException;

    /**
     * Imposta la data di termine della trasformazione precedentemente inserita nella tabella di monitoraggio ed i
     * codici di errore.
     *
     * @param trasformazione
     *            <em>Trasformazione</em> RER.
     * @param messaggio
     *            eventuale eccezione esterna. Il metodo gestisce il caso in cui il messaggio sia nullo
     * 
     * @throws TrasformazioneException
     *             nel caso in cui la Trasformazione non sia stata precedentemente censita.
     */
    public void scartaTrasformazione(Trasformazione trasformazione, String messaggio) throws TrasformazioneException;

    /**
     * Ottieni tutti i record della tabella <tt>MON_EXEC_TRASF</tt> di una trasformazione in esecuzione.
     *
     * @return lista degli stati
     */
    public List<StatoTrasformazione> ottieniTrasformazioniAttive();

    /**
     * Elimina (fisicamente? logicamente?) un record relativo ala registrazione di una traformazione
     *
     * @param trasformazione
     *            <em>Trasformazione</em> RER.
     */
    public void eliminaTrasformazione(Trasformazione trasformazione);

    /**
     * Ottieni il valore del parametro di configurazione.
     *
     * @param nomeParametro
     *            nome del parametro di configurazione.
     * 
     * @return valore del parametro di configurazione.
     */
    public String ottieniParametroConfigurazione(String nomeParametro);

    /**
     * Genera il report della trasformazione.
     * 
     * @param trasformazione
     * 
     * @return un xml contenente il report di trasformazione
     */
    public String generaReport(Trasformazione trasformazione);

    /**
     * Elimina i dati raccolti per generare un report di trasformazione.
     * 
     * @param trasformazione
     */
    public void pulisciReport(Trasformazione trasformazione);

    // MEV25024
    public List<StatoTrasformazione> ottieniTrasformazioniInCoda();

    public List<StatoTrasformazione> getStoricoTrasformazioni(Date startDate, Date endDate, int numResults);
}
