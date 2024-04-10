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

package it.eng.parer.kettle.server.config;

import it.eng.parer.kettle.lite.jpa.MonLog;
import it.eng.parer.kettle.server.persistence.lite.dao.ReportRepository;
import java.util.Date;
import org.pentaho.di.core.logging.KettleLoggingEvent;
import org.pentaho.di.core.logging.KettleLoggingEventListener;
import org.pentaho.di.core.logging.LogMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Custom Logger per i passi di un job. Grazie a questo listener posso scrivere sul file di log dell'applicazione web
 * ciò che accade durante una trasformazione.
 *
 * La semantica di traduzione dei livelli di log è stata ottenuta da:
 * https://help.pentaho.com/Documentation/8.1/Setup/Administration/Performance_Monitoring/PDI_Logging
 *
 * @author Snidero_L
 */
@Component
@Scope("prototype")
public class KettleCustomFileLoggingEventListener implements KettleLoggingEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(KettleCustomFileLoggingEventListener.class);

    private long idTrasf;

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public void eventAdded(KettleLoggingEvent event) {
        try {
            Object messageObject = event.getMessage();
            if (messageObject instanceof LogMessage) {

                LogMessage message = (LogMessage) messageObject;
                switch (message.getLevel()) {
                case NOTHING:
                    break;
                case ERROR:
                    LOGGER.error(message.getMessage());

                    MonLog logEntry = new MonLog();
                    logEntry.setIdTrasf(idTrasf);
                    logEntry.setDtLog(new Date());
                    logEntry.setCdLog("Errore");
                    logEntry.setDsLog(message.getMessage());

                    reportRepository.saveAndFlush(logEntry);

                    break;
                case MINIMAL:
                    LOGGER.warn(message.getMessage());
                    break;
                case BASIC:
                    LOGGER.info(message.getMessage());
                    break;
                case DETAILED:
                    LOGGER.info(message.getMessage());
                    break;
                case DEBUG:
                    LOGGER.debug(message.getMessage());
                    break;
                case ROWLEVEL:
                    LOGGER.trace(message.getMessage());
                    break;
                default:
                    LOGGER.debug(message.getMessage());
                }

            }
        } catch (Exception e) {
            LOGGER.error("Errore durante la gestione dell'evento di log ", e);
        }
    }

    public long getIdTrasf() {
        return idTrasf;
    }

    public void setIdTrasf(long idMonTrasf) {
        this.idTrasf = idMonTrasf;
    }
}
