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

import it.eng.parer.kettle.server.persistence.service.GestoreTrasformazioniImpl;
import it.eng.parer.kettle.service.DataService;
import it.eng.parer.kettle.service.GestoreTrasformazioni;
import it.eng.parer.kettle.ws.client.S3ClientBean;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Configurazione dei bean di spring per la parte di kettle.
 * 
 * @author Snidero_L
 */
@Configuration
@EnableAsync
public class KettleBeanConfig {
    @Autowired
    private DataService dataService;

    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    @Bean
    public GestoreTrasformazioni gestoreTrasformazioni() {
        GestoreTrasformazioniImpl gestoreTrasformazioni = new GestoreTrasformazioniImpl();
        gestoreTrasformazioni.setDataService(dataService);
        return gestoreTrasformazioni;
    }

    @Bean
    @Scope("singleton")
    public S3ClientBean s3ClientBean() {
        return new S3ClientBean();
    }

    /**
     * Definisce il thread pool per l'esecuzione dei task. Vedi https://jira.spring.io/browse/SPR-14271 .
     * http://www.baeldung.com/spring-async
     *
     * @return esecuotore di spring
     */
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        Integer numeroTrasformazioniConcorrenti = Integer
                .parseInt(dataService.ottieniParametroConfigurazione("transformation.concurrency"));
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(numeroTrasformazioniConcorrenti);
        executor.setMaxPoolSize(numeroTrasformazioniConcorrenti);
        return executor;
    }

}
