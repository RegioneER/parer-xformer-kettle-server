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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.parer.kettle.lite.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Tabella dei <em>report</em>.
 *
 * @author Cappelli_F
 */
@Entity
@Table(name = "MON_LOG")
public class MonLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MON_LOG")
    private long idMonLog;

    @Column(name = "ID_EXEC_TRASF")
    private long idTrasf;

    @Column(name = "DT_LOG")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtLog;

    @Column(name = "CD_LOG")
    private String cdLog;

    @Lob()
    @Column(name = "DS_LOG")
    private String dsLog;

    @Column(name = "FL_ESCAPE_XML")
    private String flEscapeXml;

    public MonLog() {
    }

    public long getIdMonLog() {
        return idMonLog;
    }

    public void setIdMonLog(long idMonLog) {
        this.idMonLog = idMonLog;
    }

    public long getIdTrasf() {
        return idTrasf;
    }

    public void setIdTrasf(long idTrasf) {
        this.idTrasf = idTrasf;
    }

    public Date getDtLog() {
        return dtLog;
    }

    public void setDtLog(Date dtLog) {
        this.dtLog = dtLog;
    }

    public String getCdLog() {
        return cdLog;
    }

    public void setCdLog(String cdLog) {
        this.cdLog = cdLog;
    }

    public String getDsLog() {
        return dsLog;
    }

    public void setDsLog(String dsLog) {
        this.dsLog = dsLog;
    }

    public String getFlEscapeXml() {
        return flEscapeXml;
    }

    public void setFlEscapeXml(String flEscapeXml) {
        this.flEscapeXml = flEscapeXml;
    }
}
