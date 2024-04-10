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

import it.eng.parer.kettle.model.KettleCrudException;
import it.eng.parer.kettle.model.KettleJob;
import it.eng.parer.kettle.model.KettleTransformation;
import it.eng.parer.kettle.model.Parametro;
import it.eng.parer.kettle.model.Trasformazione;
import it.eng.parer.kettle.model.TrasformazioneException;
import java.util.List;

/**
 * Classe deputata alla gestione delle trasformazioni. In questa interfaccia vengono deifiniti i metodi per gestire il
 * ciclo di vita di una <em>Trasformazione</em>.
 *
 * @author Snidero_L
 */
public interface GestoreTrasformazioni {

    /**
     * Controllo preliminare sulla possibilità di eseguire la <em>Trasformazione</em>.
     *
     * @param trasformazione
     *            <em>Trasformazione</em> intesa nel gergo RER.
     * @throws TrasformazioneException
     *             Il messaggio contiene la causa dell'eccezione.
     * @return si può avviare una trasformazione oppure no?
     */
    public boolean possoEseguireTrasformazione(Trasformazione trasformazione) throws TrasformazioneException;

    /**
     * Cuore dell'interfaccia. Questo metodo ,che verrà implementato come metodo asincrono, si occupa di effettuare la
     * trasformazione kettle.
     *
     * @param trasformazione
     *            <em>Trasformazione</em> intesa nel gergo RER.
     */
    public void eseguiTrasformazione(Trasformazione trasformazione);

    /**
     * Aggiungi un nuovo <em>Job</em> nel repository kettle.
     *
     * @param job
     *            nel gergo di Pentaho
     * @throws KettleCrudException
     *             Il messaggio contiene la causa dell'eccezione.
     */
    public void inserisciJob(KettleJob job) throws KettleCrudException;

    /**
     * Aggiungi una nuova <em>Transformation</em> nel repository kettle.
     *
     * @param transformation
     *            nel gergo di Pentaho
     * @throws KettleCrudException
     *             Il messaggio contiene la causa dell'eccezione.
     */
    public void inserisciTransformation(KettleTransformation transformation) throws KettleCrudException;

    /**
     * Elimina una nuova <em>cartella</em> dal repository.
     *
     * @param nomeCartella
     *            nome della cartella associata alla <em>Trasformazione</em>.
     * @throws KettleCrudException
     *             Il messaggio contiene la causa dell'eccezione.
     */
    public void eliminaCartella(String nomeCartella) throws KettleCrudException;

    /**
     * Aggiunti una nuova <em>cartella</em> nel repository kettle.
     *
     * @param nomeCartella
     *            nome della catella da associare alla <em>Trasformazione</em>.
     * @throws KettleCrudException
     *             Il messaggio contiene la causa dell'eccezione.
     */
    public void inserisciCartella(String nomeCartella) throws KettleCrudException;

    /**
     * Esponi la lista dei parametri della <em>Trasformazione</em> di nome <tt>nomeTrasformazione</tt>.
     *
     * @param nomeTrasformazione
     *            nome della <em>Trasformazione</em> .
     * @return la lista dei parametri della <em>Trasformazione</em> identificata dal nome <tt>nomeTrasformazione</tt>
     *         oppure null.
     */
    public List<Parametro> recuperaParametri(String nomeTrasformazione);

    /**
     * Controlla l'esistenza di una determninata cartella nel repository di kettle
     * 
     * @param nomeCartella
     * @return esistenza cartella
     */
    public boolean esistenzaCartella(String nomeCartella);
}
