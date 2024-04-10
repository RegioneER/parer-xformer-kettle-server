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

package it.eng.parer.kettle.server.persistence.service;

import it.eng.parer.kettle.jpa.MonExecTrasf;
import it.eng.parer.kettle.lite.jpa.MonLog;
import it.eng.parer.kettle.model.Parametro;
import it.eng.parer.kettle.model.StatoTrasformazione;
import it.eng.parer.kettle.model.Trasformazione;
import it.eng.parer.kettle.model.TrasformazioneException;
import it.eng.parer.kettle.server.persistence.dao.MonitoraggioRepository;
import it.eng.parer.kettle.server.persistence.lite.dao.ReportRepository;
import it.eng.parer.kettle.service.DataService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Cappelli_F
 */
@Service
@Transactional
public class DataServiceImpl implements DataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    private Environment env;

    @Autowired
    private MonitoraggioRepository monitoraggioRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean accettaTrasformazione(Trasformazione trasformazione) {
        int lunghezzaMassimacodaDiTrasformazione = Integer
                .parseInt(ottieniParametroConfigurazione("transformation.queue"));

        long lunghezzaCorrenteCodaDiTrasformazione = monitoraggioRepository.countByNmKsInstanceAndTiStatoTrasfIn(
                ottieniParametroConfigurazione("config.instance_name"),
                MonExecTrasf.STATO_TRASFORMAZIONE.IN_CODA_TRASFORMAZIONE,
                MonExecTrasf.STATO_TRASFORMAZIONE.TRASFORMAZIONE_IN_CORSO);

        if (lunghezzaCorrenteCodaDiTrasformazione >= lunghezzaMassimacodaDiTrasformazione) {
            return false;
        }

        long idPigObject = trasformazione.getIdOggettoPing();
        MonExecTrasf monitoraggio = monitoraggioRepository.findByIdPigObjectAndNmKsInstance(idPigObject,
                ottieniParametroConfigurazione("config.instance_name"));
        if (monitoraggio != null) {
            // se è già in coda o in corso non accetare la trasformazione, ma potrebbe essere in stato di errore o già
            // trasformata
            if (monitoraggio.getTiStatoTrasf() == MonExecTrasf.STATO_TRASFORMAZIONE.IN_CODA_TRASFORMAZIONE
                    || monitoraggio.getTiStatoTrasf() == MonExecTrasf.STATO_TRASFORMAZIONE.TRASFORMAZIONE_IN_CORSO) {

                return false;
            }
            // vanno sbiancati alcuni campi
            monitoraggio.setDtInizioTrasf(null);
            monitoraggio.setDtFineTrasf(null);
            monitoraggio.setCdErrTrasf(null);
            monitoraggio.setDsErrTrasf(null);
        } else {
            monitoraggio = new MonExecTrasf();
            monitoraggio.setIdPigObject(idPigObject);
        }

        monitoraggio.setNmKsInstance(ottieniParametroConfigurazione("config.instance_name"));
        monitoraggio.setNmTrasf(trasformazione.getNomeTrasformazione());
        monitoraggio.setTiStatoTrasf(MonExecTrasf.STATO_TRASFORMAZIONE.IN_CODA_TRASFORMAZIONE);
        monitoraggio.setDtInvocazioneWs(new Date());
        monitoraggio.setDsStatoTrasf("La trasformazione per l'id oggetto " + idPigObject + " è stata accettata.");
        monitoraggioRepository.save(monitoraggio);

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void iniziaTrasformazione(Trasformazione trasformazione) {
        long idPigObject = trasformazione.getIdOggettoPing();
        MonExecTrasf monitoraggio = monitoraggioRepository.findByIdPigObjectAndNmKsInstance(idPigObject,
                ottieniParametroConfigurazione("config.instance_name"));
        if (monitoraggio == null) {
            throw new TrasformazioneException("Impossibile trovare la trasformazione per l'id oggetto  " + idPigObject);
        }

        Date startDate = new Date();

        monitoraggio.setDtInizioTrasf(startDate);
        monitoraggio.setTiStatoTrasf(MonExecTrasf.STATO_TRASFORMAZIONE.TRASFORMAZIONE_IN_CORSO);
        monitoraggio.setDsStatoTrasf("La trasformazione per l'id oggetto " + idPigObject + " è in corso.");
        monitoraggioRepository.save(monitoraggio);

        // inizializza il report
        MonLog logEntry = new MonLog();
        logEntry.setIdTrasf(monitoraggio.getIdExecTrasf());
        logEntry.setDtLog(new Date());
        logEntry.setCdLog("IdTrasformazione");
        logEntry.setDsLog(trasformazione.getNomeTrasformazione());

        reportRepository.save(logEntry);

        logEntry = new MonLog();
        logEntry.setIdTrasf(monitoraggio.getIdExecTrasf());
        logEntry.setDtLog(new Date());
        logEntry.setCdLog("ParametriTrasformazione");

        StringBuilder parameters = new StringBuilder();
        for (Parametro parametro : trasformazione.getParametri()) {
            parameters = parameters.length() > 0
                    ? parameters.append(" | ").append(parametro.getNomeParametro()).append(" : ")
                            .append(parametro.getValoreParametro())
                    : parameters.append(parametro.getNomeParametro()).append(" : ")
                            .append(parametro.getValoreParametro());
        }

        logEntry.setDsLog(parameters.toString());
        reportRepository.save(logEntry);

        logEntry = new MonLog();
        logEntry.setIdTrasf(monitoraggio.getIdExecTrasf());
        logEntry.setDtLog(new Date());
        logEntry.setCdLog("IdPigObject");
        logEntry.setDsLog(String.valueOf(trasformazione.getIdOggettoPing()));

        reportRepository.save(logEntry);

        logEntry = new MonLog();
        logEntry.setIdTrasf(monitoraggio.getIdExecTrasf());
        logEntry.setDtLog(new Date());
        logEntry.setCdLog("DataInizio");
        logEntry.setDsLog(startDate.toString());

        reportRepository.save(logEntry);

        // salva l'identificativo del report
        trasformazione.setIdTrasfReport(monitoraggio.getIdExecTrasf());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void terminaTrasformazione(Trasformazione trasformazione) {
        long idPigObject = trasformazione.getIdOggettoPing();
        MonExecTrasf monitoraggio = monitoraggioRepository.findByIdPigObjectAndNmKsInstance(idPigObject,
                ottieniParametroConfigurazione("config.instance_name"));
        if (monitoraggio == null) {
            throw new TrasformazioneException("Impossibile trovare la trasformazione per l'id oggetto  " + idPigObject);
        }

        Date endDate = new Date();

        monitoraggio.setDtFineTrasf(endDate);
        monitoraggio.setTiStatoTrasf(MonExecTrasf.STATO_TRASFORMAZIONE.TRASFORMAZIONE_TERMINATA);
        monitoraggio
                .setDsStatoTrasf("La trasformazione per l'id oggetto " + idPigObject + " è terminata correttamente.");
        monitoraggioRepository.save(monitoraggio);

        // finalizza il report
        MonLog logEntry = new MonLog();
        logEntry.setIdTrasf(monitoraggio.getIdExecTrasf());
        logEntry.setDtLog(new Date());
        logEntry.setCdLog("DataFine");
        logEntry.setDsLog(endDate.toString());

        reportRepository.save(logEntry);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void scartaTrasformazione(Trasformazione trasformazione, String message) throws TrasformazioneException {
        long idPigObject = trasformazione.getIdOggettoPing();
        MonExecTrasf monitoraggio = monitoraggioRepository.findByIdPigObjectAndNmKsInstance(idPigObject,
                ottieniParametroConfigurazione("config.instance_name"));
        if (monitoraggio == null) {
            throw new TrasformazioneException("Impossibile trovare la trasformazione per l'id oggetto  " + idPigObject);
        }

        Date endDate = new Date();

        monitoraggio.setDtFineTrasf(endDate);
        monitoraggio.setTiStatoTrasf(MonExecTrasf.STATO_TRASFORMAZIONE.ERRORE_TRASFORMAZIONE);
        monitoraggio.setDsStatoTrasf(
                "La trasformazione per l'id oggetto " + idPigObject + " è terminata in maniera errata.");
        monitoraggio.setCdErrTrasf("ERRORE");
        if (message != null && !message.isEmpty()) {
            monitoraggio.setDsErrTrasf(message.substring(0, Math.min(message.length(), 1024)));
        } else {
            monitoraggio.setDsErrTrasf("Errore generico");
        }
        monitoraggioRepository.save(monitoraggio);

        // finalizza il report
        MonLog logEntry = new MonLog();
        logEntry.setIdTrasf(monitoraggio.getIdExecTrasf());
        logEntry.setDtLog(new Date());
        logEntry.setCdLog("DataFine");
        logEntry.setDsLog(endDate.toString());

        reportRepository.save(logEntry);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminaTrasformazione(Trasformazione trasformazione) {
        monitoraggioRepository.pulisciTrasformazione(trasformazione.getIdOggettoPing(),
                ottieniParametroConfigurazione("config.instance_name"));
    }

    @Override
    public List<StatoTrasformazione> ottieniTrasformazioniAttive() {
        List<StatoTrasformazione> trasformazioniAttive = new ArrayList<>();
        List<MonExecTrasf> monitoraggi = monitoraggioRepository.findByNmKsInstanceAndTiStatoTrasf(
                ottieniParametroConfigurazione("config.instance_name"),
                MonExecTrasf.STATO_TRASFORMAZIONE.TRASFORMAZIONE_IN_CORSO);
        for (MonExecTrasf monitoraggio : monitoraggi) {
            trasformazioniAttive.add(getStato(monitoraggio));
        }
        return trasformazioniAttive;
    }

    @Override
    public List<StatoTrasformazione> ottieniTrasformazioniInCoda() {
        List<StatoTrasformazione> trasformazioniInCoda = new ArrayList<>();
        List<MonExecTrasf> monitoraggi = monitoraggioRepository.findByNmKsInstanceAndTiStatoTrasf(
                ottieniParametroConfigurazione("config.instance_name"),
                MonExecTrasf.STATO_TRASFORMAZIONE.IN_CODA_TRASFORMAZIONE);
        for (MonExecTrasf monitoraggio : monitoraggi) {
            trasformazioniInCoda.add(getStato(monitoraggio));
        }
        return trasformazioniInCoda;
    }

    private static StatoTrasformazione getStato(MonExecTrasf monitoraggio) {
        StatoTrasformazione stato = new StatoTrasformazione();
        stato.setIdOggettoPing(monitoraggio.getIdPigObject());
        stato.setNomeTrasformazione(monitoraggio.getNmTrasf());

        stato.setCodiceStatoTrasformazione(monitoraggio.getTiStatoTrasf().name());
        if (monitoraggio.getCdErrTrasf() == null) {
            stato.setDescrizioneStatoTrasformazione(monitoraggio.getDsStatoTrasf());
        } else {
            // CODICE_ERRORE: DESCRIZIONE_ERRORE
            stato.setDescrizioneStatoTrasformazione(monitoraggio.getCdErrTrasf() + ": " + monitoraggio.getDsErrTrasf());
        }
        stato.setDataInvocazioneWs(monitoraggio.getDtInvocazioneWs());
        stato.setDataInizioTrasformazione(monitoraggio.getDtInizioTrasf());
        stato.setDataFineTrasformazione(monitoraggio.getDtFineTrasf());
        return stato;
    }

    @Override
    public String ottieniParametroConfigurazione(String nomeParametro) {
        String parametro = env.getProperty(nomeParametro);
        if (parametro == null) {
            throw new IllegalArgumentException("Parametro " + nomeParametro + " non esistente");
        }
        return parametro;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void reinizializzaEPulisci() {
        List<MonExecTrasf> trasformazioni = monitoraggioRepository.findByNmKsInstanceAndTiStatoTrasf(
                ottieniParametroConfigurazione("config.instance_name"),
                MonExecTrasf.STATO_TRASFORMAZIONE.TRASFORMAZIONE_IN_CORSO);
        for (MonExecTrasf trasformazione : trasformazioni) {
            trasformazione.setTiStatoTrasf(MonExecTrasf.STATO_TRASFORMAZIONE.ERRORE_TRASFORMAZIONE);
            monitoraggioRepository.save(trasformazione);
        }

        trasformazioni = monitoraggioRepository.findByNmKsInstanceAndTiStatoTrasf(
                ottieniParametroConfigurazione("config.instance_name"),
                MonExecTrasf.STATO_TRASFORMAZIONE.IN_CODA_TRASFORMAZIONE);
        for (MonExecTrasf trasformazione : trasformazioni) {
            trasformazione.setTiStatoTrasf(MonExecTrasf.STATO_TRASFORMAZIONE.ERRORE_TRASFORMAZIONE);
            monitoraggioRepository.save(trasformazione);
        }
    }

    @Override
    public String generaReport(Trasformazione trasformazione) {
        LOGGER.info("Inizio generazione report per " + trasformazione.getIdOggettoPing());

        Document report = DocumentHelper.createDocument();
        Element root = report.addElement("Report");
        Element errorsSection = root.addElement("Errori");

        List<MonLog> logs = reportRepository.findByIdTrasf(trasformazione.getIdTrasfReport());

        root.addAttribute("pigId", Long.toString(trasformazione.getIdOggettoPing()));
        for (MonLog log : logs) {
            LOGGER.debug("REPORT LOG ENTRY: " + log.getCdLog() + " : " + log.getDsLog());

            switch (log.getCdLog().trim()) {
            case "Errore":
                Element errore = errorsSection.addElement("Errore");
                errore.addAttribute("time", log.getDtLog().toString());
                errore.addText(StringEscapeUtils.escapeXml(log.getDsLog()));
                break;
            default: {
                try {
                    addReportEntry(report, log);
                } catch (Exception ex) {
                    LOGGER.warn(ex.getMessage());
                }
            }
                break;

            }
        }

        LOGGER.info("Fine generazione report per " + trasformazione.getIdOggettoPing());

        return report.asXML();
    }

    private void addReportEntry(Document report, MonLog log) throws Exception {
        String[] cdLogs = log.getCdLog().split("/");
        String dsLog = log.getDsLog();

        Element currentElement = report.getRootElement();
        String xpath = "/Report";
        for (int i = 0; i < cdLogs.length - 1; i++) {
            if (!cdLogs[i].isEmpty() && isValidName(cdLogs[i])) {
                xpath = xpath + "/" + cdLogs[i];
                Element nextElement = (Element) report.getRootElement().selectSingleNode(xpath);
                if (nextElement == null) {
                    currentElement = currentElement.addElement(cdLogs[i]);
                } else {
                    currentElement = nextElement;
                }
            } else {
                throw new Exception("REPORT: " + cdLogs[i] + " non è un elemeto XML valido.");
            }
        }

        if (!cdLogs[cdLogs.length - 1].isEmpty() && isValidName(cdLogs[cdLogs.length - 1])) {
            if (log.getFlEscapeXml() == null || log.getFlEscapeXml().equals("1")) {
                currentElement = currentElement.addElement(cdLogs[cdLogs.length - 1]);
                currentElement.addAttribute("time", log.getDtLog().toString());
                currentElement.addText(dsLog);
            } else {
                StringBuilder unenscapedElement = new StringBuilder();
                unenscapedElement.append("<").append(cdLogs[cdLogs.length - 1]).append(" time=\"")
                        .append(log.getDtLog().toString()).append("\" >");
                unenscapedElement.append(dsLog);
                unenscapedElement.append("</").append(cdLogs[cdLogs.length - 1]).append(">");
                currentElement.add(DocumentHelper.parseText(unenscapedElement.toString()).getRootElement());
            }
        } else {
            throw new Exception("REPORT: " + cdLogs[cdLogs.length - 1] + " non è un elemeto XML valido.");
        }
    }

    @Override
    public void pulisciReport(Trasformazione trasformazione) {
        reportRepository.eliminaTuttiLogDiTrasformazione(trasformazione.getIdTrasfReport());
    }

    // MEV 25024
    @Override
    public List<StatoTrasformazione> getStoricoTrasformazioni(Date startDate, Date endDate, int numResults) {
        List<StatoTrasformazione> storicoTrasformazioni = new ArrayList<>();

        List<MonExecTrasf> monitoraggi = monitoraggioRepository
                .findByNmKsInstanceAndDtInizioTrasfBetweenAndTiStatoTrasfIn(PageRequest.of(0, numResults),
                        ottieniParametroConfigurazione("config.instance_name"), startDate, endDate,
                        MonExecTrasf.STATO_TRASFORMAZIONE.ERRORE_TRASFORMAZIONE,
                        MonExecTrasf.STATO_TRASFORMAZIONE.TRASFORMAZIONE_TERMINATA)
                .getContent();

        for (MonExecTrasf monitoraggio : monitoraggi) {
            storicoTrasformazioni.add(getStato(monitoraggio));
        }

        return storicoTrasformazioni;
    }

    /**
     * Check to see if a string is a valid Name
     *
     * @param name
     *            string to check
     * @return true if name is a valid Name
     */
    private boolean isValidName(String name) {
        final int length = name.length();
        if (length == 0) {
            return false;
        }
        String firstChar = name.substring(0, 1);
        if (!isNameStart(firstChar)) {
            return false;
        }

        String remainingChars = name.substring(1);
        if (!isName(remainingChars)) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the specified character is a valid name start character as defined by production [5] in the XML
     * 1.0 specification.
     *
     * @param c
     *            The character to check.
     */
    private boolean isNameStart(String c) {
        return (c.matches("[a-zA-Z_:]"));
    }

    /**
     * Returns true if the specified character is a valid name character as defined by production [4] in the XML 1.0
     * specification.
     *
     * @param c
     *            The character to check.
     */
    private static boolean isName(String c) {
        return (c.matches("([a-zA-Z0-9_:.])*"));
    }
}
