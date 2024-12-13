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

package it.eng.parer.kettle.server;

/**
 *
 * @author Cappelli_F
 */
public class Constants {
    private Constants() {
        // solo per non far lamentare sonarqube
    }

    public static final String XF_OBJECT_STORAGE_KEY = "XF_OBJECT_STORAGE_KEY";
    public static final String XF_OBJECT_STORAGE_BUCKET = "XF_OBJECT_STORAGE_BUCKET";
    public static final String XF_INPUT_FILE_NAME = "XF_INPUT_FILE_NAME";
    public static final String XF_TMP_DIR = "XF_TMP_DIR";
}
