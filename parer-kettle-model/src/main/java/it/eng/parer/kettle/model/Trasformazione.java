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
import java.util.List;

/**
 * Bean che identifica le informazioni necessarie ad identificare una <em>Trasformazione</em> intesa nel gergo RER.
 *
 * @author Snidero_L
 */
public class Trasformazione implements Serializable {

    private static final long serialVersionUID = -2359465051207551433L;
    private String nomeTrasformazione;
    private long IdOggettoPing;
    private List<Parametro> parametri;
    private long idTrasfReport;

    public Trasformazione() {

    }

    public String getNomeTrasformazione() {
        return nomeTrasformazione;
    }

    public void setNomeTrasformazione(String nomeTrasformazione) {
        this.nomeTrasformazione = nomeTrasformazione;
    }

    public long getIdOggettoPing() {
        return IdOggettoPing;
    }

    public void setIdOggettoPing(long IdOggettoPing) {
        this.IdOggettoPing = IdOggettoPing;
    }

    public List<Parametro> getParametri() {
        return parametri;
    }

    public void setParametri(List<Parametro> parametri) {
        this.parametri = parametri;
    }

    public long getIdTrasfReport() {
        return idTrasfReport;
    }

    public void setIdTrasfReport(long idTrasfReport) {
        this.idTrasfReport = idTrasfReport;
    }
}
