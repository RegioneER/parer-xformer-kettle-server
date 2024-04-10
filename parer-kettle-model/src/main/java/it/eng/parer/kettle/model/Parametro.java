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
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Snidero_L
 */
@XmlRootElement(name = "parametro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Parametro implements Serializable {

    private static final long serialVersionUID = -2527654965784342945L;
    @XmlElement(name = "nome", nillable = false, required = true)
    private String nomeParametro;
    @XmlElement(name = "valore", nillable = false, required = true)
    private String valoreParametro;

    public Parametro() {

    }

    public Parametro(String nome, String valore) {
        this.nomeParametro = nome;
        this.valoreParametro = valore;
    }

    public String getNomeParametro() {
        return nomeParametro;
    }

    public void setNomeParametro(String nomeParametro) {
        this.nomeParametro = nomeParametro;
    }

    public String getValoreParametro() {
        return valoreParametro;
    }

    public void setValoreParametro(String valoreParametro) {
        this.valoreParametro = valoreParametro;
    }

    @Override
    public String toString() {
        return "Parametro{" + "nomeParametro=" + nomeParametro + ", valoreParametro=" + valoreParametro + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Parametro other = (Parametro) obj;
        if (!Objects.equals(this.nomeParametro, other.nomeParametro)) {
            return false;
        }
        if (!Objects.equals(this.valoreParametro, other.valoreParametro)) {
            return false;
        }
        return true;
    }

}
