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

package it.eng.parer.kettle.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Tabella che censisce lo stato di una <em>Trasformazione</em>.
 *
 * @author Snidero_L
 */
@Entity
@Table(name = "MON_EXEC_TRASF")
public class MonExecTrasf implements Serializable {

    private static final long serialVersionUID = 583539094769551130L;

    public enum STATO_TRASFORMAZIONE {
        IN_CODA_TRASFORMAZIONE, TRASFORMAZIONE_IN_CORSO, TRASFORMAZIONE_TERMINATA, ERRORE_TRASFORMAZIONE
    }

    @Id
    @SequenceGenerator(name = "MON_EXEC_TRASF_IDEXECTRASF_GENERATOR", sequenceName = "SMON_EXEC_TRASF", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MON_EXEC_TRASF_IDEXECTRASF_GENERATOR")
    @Column(name = "ID_EXEC_TRASF")
    private long idExecTrasf;

    @Column(name = "ID_PIG_OBJECT")
    private long idPigObject;

    @Column(name = "TI_STATO_TRASF")
    @Enumerated(EnumType.STRING)
    private STATO_TRASFORMAZIONE tiStatoTrasf;

    @Column(name = "NM_TRASF")
    private String nmTrasf;

    @Column(name = "DT_INVOCAZIONE_WS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInvocazioneWs;

    @Column(name = "DT_INIZIO_TRASF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInizioTrasf;

    @Column(name = "DT_FINE_TRASF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtFineTrasf;

    @Column(name = "DS_STATO_TRASF")
    private String dsStatoTrasf;

    @Column(name = "CD_ERR_TRASF")
    private String cdErrTrasf;

    @Column(name = "DS_ERR_TRASF")
    private String dsErrTrasf;

    @Column(name = "NM_KS_INSTANCE")
    private String nmKsInstance;

    public MonExecTrasf() {
        // per usi futuri.
    }

    public long getIdExecTrasf() {
        return idExecTrasf;
    }

    public void setIdExecTrasf(long idExecTrasf) {
        this.idExecTrasf = idExecTrasf;
    }

    public long getIdPigObject() {
        return idPigObject;
    }

    public void setIdPigObject(long idPigObject) {
        this.idPigObject = idPigObject;
    }

    public STATO_TRASFORMAZIONE getTiStatoTrasf() {
        return tiStatoTrasf;
    }

    public void setTiStatoTrasf(STATO_TRASFORMAZIONE tiStatoTrasf) {
        this.tiStatoTrasf = tiStatoTrasf;
    }

    public String getNmTrasf() {
        return nmTrasf;
    }

    public void setNmTrasf(String nmTrasf) {
        this.nmTrasf = nmTrasf;
    }

    public Date getDtInvocazioneWs() {
        return dtInvocazioneWs;
    }

    public void setDtInvocazioneWs(Date dtInvocazioneWs) {
        this.dtInvocazioneWs = dtInvocazioneWs;
    }

    public Date getDtInizioTrasf() {
        return dtInizioTrasf;
    }

    public void setDtFineTrasf(Date dtFineTrasf) {
        this.dtFineTrasf = dtFineTrasf;
    }

    public Date getDtFineTrasf() {
        return dtFineTrasf;
    }

    public void setDtInizioTrasf(Date dtInizioTrasf) {
        this.dtInizioTrasf = dtInizioTrasf;
    }

    public String getDsStatoTrasf() {
        return dsStatoTrasf;
    }

    public void setDsStatoTrasf(String dsStatoTrasf) {
        this.dsStatoTrasf = dsStatoTrasf;
    }

    public String getCdErrTrasf() {
        return cdErrTrasf;
    }

    public void setCdErrTrasf(String cdErrTrasf) {
        this.cdErrTrasf = cdErrTrasf;
    }

    public String getDsErrTrasf() {
        return dsErrTrasf;
    }

    public void setDsErrTrasf(String dsErrTrasf) {
        this.dsErrTrasf = dsErrTrasf;
    }

    public String getNmKsInstance() {
        return nmKsInstance;
    }

    public void setNmKsInstance(String nmKsInstance) {
        this.nmKsInstance = nmKsInstance;
    }
}
