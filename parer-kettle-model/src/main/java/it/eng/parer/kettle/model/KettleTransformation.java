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
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Kettle Transformation. <em>Attenzione</em> non va confusa non la <em>Trasformazione</em> in gergo RER.
 *
 * @author Snidero_L
 */
@XmlRootElement(name = "kettleTrasformation")
@XmlAccessorType(XmlAccessType.FIELD)
public class KettleTransformation implements Serializable {

    private static final long serialVersionUID = 310595409550548783L;

    @XmlElement(name = "descrittoreXmlTransformation", nillable = false, required = true)
    private DataHandler transformationDescriptor;
    @XmlElement(nillable = false, required = true)
    private String versione;

    public KettleTransformation() {

    }

    public DataHandler getTransformationDescriptor() {
        return transformationDescriptor;
    }

    public void setTransformationDescriptor(DataHandler transformationDescriptor) {
        this.transformationDescriptor = transformationDescriptor;
    }

    public String getVersione() {
        return versione;
    }

    public void setVersione(String versione) {
        this.versione = versione;
    }

}
