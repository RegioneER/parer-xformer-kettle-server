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

/**
 * Eccezione utilizzata per definire le eccezione relative alle operazione di tipo <em>CRUD</em>.
 *
 *
 * @author Snidero_L
 */
public class KettleCrudException extends RuntimeException {

    private static final long serialVersionUID = 4758118355566222884L;

    public KettleCrudException(String message) {
        super(message);
    }

    public KettleCrudException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
