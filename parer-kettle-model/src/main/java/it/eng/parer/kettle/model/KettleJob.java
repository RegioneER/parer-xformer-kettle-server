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
import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Job Kettle.
 *
 * @author Snidero_L
 */
@XmlRootElement(name = "kettleJob")
@XmlAccessorType(XmlAccessType.FIELD)
public class KettleJob implements Serializable {

    private static final long serialVersionUID = -7187664167417470173L;

    @XmlElement(name = "descrittoreXmlJob", nillable = false, required = true)
    private DataHandler jobDescriptor;
    @XmlElement(nillable = false, required = true)
    private String versione;

    public KettleJob() {

    }

    public DataHandler getJobDescriptor() {
        return jobDescriptor;
    }

    public void setJobDescriptor(DataHandler jobDescriptor) {
        this.jobDescriptor = jobDescriptor;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }

}
