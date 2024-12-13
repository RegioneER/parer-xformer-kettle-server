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

import java.util.Properties;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configurazione dello strato di persistenza lite usando H2 .
 *
 * @author Cappelli_F
 */
@Configuration
@EnableTransactionManagement
@ComponentScan({ "it.eng.parer.kettle.server.persistence" })
@EnableJpaRepositories(basePackages = {
        "it.eng.parer.kettle.server.persistence.lite.dao" }, entityManagerFactoryRef = "liteEntityManagerFactory", transactionManagerRef = "liteTransactionManager")
public class LitePersistanceConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource liteDatasource() throws NamingException {
        String repoJNDI = env.getProperty("jndi.kettle_lite_ds");

        if (repoJNDI != null) {
            return (DataSource) new JndiTemplate().lookup(repoJNDI);
        } else {
            throw new NamingException("jndi.kettle_lite_ds not found.");
        }
    }

    // Bean per @EnableJpaRepositories
    @Bean
    public LocalContainerEntityManagerFactoryBean liteEntityManagerFactory() throws NamingException {

        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(liteDatasource());
        em.setPackagesToScan("it.eng.parer.kettle.lite.jpa");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());

        // rest of entity manager configuration
        return em;
    }

    // Bean per @EnableJpaRepositories
    @Bean
    public PlatformTransactionManager liteTransactionManager() throws NamingException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(liteEntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor liteExceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    // rest of persistence configuration
    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("javax.persistence.logging.level", "DEBUG");
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        // produce output non formattato su catalina.out
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        return hibernateProperties;
    }
}
