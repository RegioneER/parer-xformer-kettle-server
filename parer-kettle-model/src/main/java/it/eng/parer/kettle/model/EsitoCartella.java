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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Snidero_L
 */
@XmlRootElement(name = "esitoCartella")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class EsitoCartella extends AbstractEsito {

    private static final long serialVersionUID = -7027799616663400377L;

    private String nomeCartella;

    public EsitoCartella() {

    }

    public EsitoCartella(String nomeCartella) {
        this.nomeCartella = nomeCartella;
    }

    @XmlElement(nillable = false, required = true)
    public String getNomeCartella() {
        return nomeCartella;
    }

    public void setNomeCartella(String nomeCartella) {
        this.nomeCartella = nomeCartella;
    }

}
