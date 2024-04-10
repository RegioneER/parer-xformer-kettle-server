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

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.apache.cxf.BusFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Cappelli_F
 */
@SpringBootApplication
public class KettleServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(KettleServerApplication.class);
        application.setBanner(new KettleServerBanner());
        application.run(args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(KettleServerApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        CXFServlet cxf = new CXFServlet();
        BusFactory.setDefaultBus(cxf.getBus());
        ServletRegistration.Dynamic cxfServlet = servletContext.addServlet("CXFServlet", cxf);
        cxfServlet.setLoadOnStartup(1);
        cxfServlet.addMapping("/services/*");
    }
}
