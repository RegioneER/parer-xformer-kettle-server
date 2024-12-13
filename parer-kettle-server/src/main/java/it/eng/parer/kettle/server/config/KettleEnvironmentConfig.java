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

import it.eng.parer.kettle.service.DataService;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.JobEntryPluginType;
import org.pentaho.di.core.plugins.PluginFolder;
import org.pentaho.di.core.plugins.PluginInterface;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.PluginTypeInterface;
import org.pentaho.di.core.plugins.StepPluginType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Cappelli_F
 */
@Configuration
public class KettleEnvironmentConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KettleEnvironmentConfig.class);

    @Autowired
    private DataService dataService;

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Inizializzazione dell'ambiente di kettle.");

        // Mette in errore le trasformazioni che erano in coda o in corso quando il sistema è stato spento.
        dataService.reinizializzaEPulisci();

        try {
            String pluginFolderPath = dataService.ottieniParametroConfigurazione("config.plugin_folder_path");
            StepPluginType.getInstance().getPluginFolders().add(new PluginFolder(pluginFolderPath, true, true));
            JobEntryPluginType.getInstance().getPluginFolders().add(new PluginFolder(pluginFolderPath, true, true));

            // Disabilito il logging in console. Potrei farlo anche via kettle.properties
            System.setProperty(Const.KETTLE_DISABLE_CONSOLE_LOGGING, "Y");

            KettleEnvironment.init(false);

        } catch (IllegalArgumentException | KettleException ex) {
            LOGGER.error("Errore nell'inizializzazione di kettle", ex);
        }

        PluginRegistry registry = PluginRegistry.getInstance();
        StringBuilder sb = new StringBuilder("Plugins: \n");
        for (Class<? extends PluginTypeInterface> pluginType : registry.getPluginTypes()) {
            sb.append("\tClass: ");
            sb.append(pluginType.getCanonicalName());
            sb.append("\n");
            for (PluginInterface plugin : registry.getPlugins(pluginType)) {
                sb.append("\t\tNome: ");
                sb.append(plugin.getName());
                sb.append("\n");
                sb.append("\t\tDescrizione: ");
                sb.append(plugin.getDescription());
                sb.append("\n");
                sb.append("\t\tIdentificativi: [");
                boolean isFirst = true;
                for (String id : plugin.getIds()) {
                    if (isFirst) {
                        isFirst = false;
                    } else {
                        sb.append(", ");
                    }
                    sb.append(id);
                }
                sb.append("]\n");
                sb.append("\t\tCategoria: ");
                sb.append(plugin.getCategory());
                sb.append("\n");
                sb.append("\t\tDirectory: ");
                sb.append(plugin.getPluginDirectory());
                sb.append("\n");
                sb.append("\t\tDoc URL: ");
                sb.append(plugin.getDocumentationUrl());
                sb.append("\n\n");
            }
        }
        LOGGER.info("{}", sb);
    }

    @PreDestroy
    public void destroy() {
        LOGGER.info("Spegnimento dell'ambiente di kettle.");
        KettleEnvironment.shutdown();
    }

}
