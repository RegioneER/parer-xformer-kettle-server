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

package it.eng.parer.kettle.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;

/**
 * Esito della chiamata a WS. Un esito di chiamata al WS è composto <em>almeno</em> da:
 * <ul>
 * <li>un esito sintetico {@link AbstractEsito#esitoSintetico} ;</li>
 * <li>una lista di 0 o più elementi di dettaglio.</li>
 * </ul>
 *
 * @author Snidero_L
 */
public abstract class AbstractEsito implements Serializable {

    private static final long serialVersionUID = 4456679559459670580L;

    /**
     * Esito di sintesi della chiamata ad un WS.
     */
    public enum ESITO_SINTETICO {
        OK, KO, CODA_PIENA
    };

    protected String dettagli;
    protected Esito.ESITO_SINTETICO esitoSintetico;

    public String getDettaglio() {
        return dettagli;
    }

    public void setDettaglio(String dettagli) {
        this.dettagli = dettagli;
    }

    @XmlElement(nillable = false, required = true)
    public Esito.ESITO_SINTETICO getEsitoSintetico() {
        return esitoSintetico;
    }

    public void setEsitoSintetico(Esito.ESITO_SINTETICO esitoSintetico) {
        this.esitoSintetico = esitoSintetico;
    }
}
