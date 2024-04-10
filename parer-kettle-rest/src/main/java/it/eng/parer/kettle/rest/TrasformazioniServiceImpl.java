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

package it.eng.parer.kettle.rest;

import it.eng.parer.kettle.model.StatoTrasformazione;
import it.eng.parer.kettle.rest.client.TrasformazioniService;
import it.eng.parer.kettle.service.DataService;
import it.eng.parer.kettle.service.GestoreTrasformazioni;
import java.util.List;

/**
 * Chiamata di esempio:
 *
 * POST verso http://localhost:8080/kettle-server/services/rest/trasformazioni/esegui con content/type :
 * application/json
 * 
 * <pre>
 * {
 *  "nomeTrasformazione" : "pippo",
 *  "idFileTrasformazione" : 42,
 *  "parametri" : [
 *      {"nomeParametro" : "param1", "valoreParametro" : "pollo"},
 *      {"nomeParametro" : "param2", "valoreParametro" : "tacchino"}
 *  ]
 * }
 * </pre>
 * 
 * oppure con curl
 * 
 * <pre>
 * curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/kettle-server/services/rest/trasformazioni/esegui --data '{"nomeTrasformazione" : "pippo", "idFileTrasformazione" : 42,  "parametri" :[{"nomeParametro" : "param1", "valoreParametro" : "pollo"},{"nomeParametro" : "param2", "valoreParametro" : "tacchino"} ] }'
 * </pre>
 *
 * @author Snidero_L
 */
public class TrasformazioniServiceImpl implements TrasformazioniService {

    private GestoreTrasformazioni gestoreTrasformazioni;
    private String versioneSoftware;
    private DataService dataService;

    public void setGestoreTrasformazioni(GestoreTrasformazioni gestoreTrasformazioni) {
        this.gestoreTrasformazioni = gestoreTrasformazioni;
    }

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    public void setVersioneSoftware(String versioneSoftware) {
        this.versioneSoftware = versioneSoftware;
    }

    @Override
    public List<StatoTrasformazione> getTrasformazioni(String status) {
        if (status != null) {

        }

        List<StatoTrasformazione> ottieniStatoTrasformazioniAttive = dataService.ottieniTrasformazioniAttive();
        return ottieniStatoTrasformazioniAttive;
    }

    @Override
    public String getVersioneSoftware() {
        return new StringBuilder("{ \"versione\" : \"").append(versioneSoftware).append("\" }").toString();
    }

}
