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
import java.util.Date;

/**
 * Stato della trasformazione. Mappa il contenuto di un record della tabella <tt>MON_EXEC_TRASF</tt>.
 *
 * @author Snidero_L
 */
public class StatoTrasformazione implements Serializable {

    private static final long serialVersionUID = -7869041531502754282L;

    private long idOggettoPing;
    private String nomeTrasformazione;
    private String codiceStatoTrasformazione;
    private String descrizioneStatoTrasformazione;
    private Date dataInvocazioneWs;
    private Date dataInizioTrasformazione;
    private Date dataFineTrasformazione;

    public long getIdOggettoPing() {
        return idOggettoPing;
    }

    public void setIdOggettoPing(long idOggettoPing) {
        this.idOggettoPing = idOggettoPing;
    }

    public String getNomeTrasformazione() {
        return nomeTrasformazione;
    }

    public void setNomeTrasformazione(String nomeTrasformazione) {
        this.nomeTrasformazione = nomeTrasformazione;
    }

    public String getCodiceStatoTrasformazione() {
        return codiceStatoTrasformazione;
    }

    public void setCodiceStatoTrasformazione(String codiceStatoTrasformazione) {
        this.codiceStatoTrasformazione = codiceStatoTrasformazione;
    }

    public String getDescrizioneStatoTrasformazione() {
        return descrizioneStatoTrasformazione;
    }

    public void setDescrizioneStatoTrasformazione(String descrizioneStatoTrasformazione) {
        this.descrizioneStatoTrasformazione = descrizioneStatoTrasformazione;
    }

    public Date getDataInvocazioneWs() {
        return dataInvocazioneWs;
    }

    public void setDataInvocazioneWs(Date dataInvocazioneWs) {
        this.dataInvocazioneWs = dataInvocazioneWs;
    }

    public Date getDataInizioTrasformazione() {
        return dataInizioTrasformazione;
    }

    public void setDataInizioTrasformazione(Date dataInizioTrasformazione) {
        this.dataInizioTrasformazione = dataInizioTrasformazione;
    }

    public Date getDataFineTrasformazione() {
        return dataFineTrasformazione;
    }

    public void setDataFineTrasformazione(Date dataFineTrasformazione) {
        this.dataFineTrasformazione = dataFineTrasformazione;
    }

}
