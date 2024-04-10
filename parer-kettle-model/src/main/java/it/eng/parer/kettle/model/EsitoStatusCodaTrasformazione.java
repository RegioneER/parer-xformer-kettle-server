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

import java.util.List;

/**
 *
 * @author Cappelli_F
 */
public class EsitoStatusCodaTrasformazione extends AbstractEsito {
    List<StatoTrasformazione> trasformazioniInCorso;
    List<StatoTrasformazione> trasformazioniInCoda;
    List<StatoTrasformazione> storicoTrasformazioni;

    public List<StatoTrasformazione> getTrasformazioniInCorso() {
        return trasformazioniInCorso;
    }

    public void setTrasformazioniInCorso(List<StatoTrasformazione> trasformazioniInCorso) {
        this.trasformazioniInCorso = trasformazioniInCorso;
    }

    public List<StatoTrasformazione> getStoricoTrasformazioni() {
        return storicoTrasformazioni;
    }

    public void setStoricoTrasformazioni(List<StatoTrasformazione> storicoTrasformazioni) {
        this.storicoTrasformazioni = storicoTrasformazioni;
    }

    public List<StatoTrasformazione> getTrasformazioniInCoda() {
        return trasformazioniInCoda;
    }

    public void setTrasformazioniInCoda(List<StatoTrasformazione> trasformazioniInCoda) {
        this.trasformazioniInCoda = trasformazioniInCoda;
    }

}
