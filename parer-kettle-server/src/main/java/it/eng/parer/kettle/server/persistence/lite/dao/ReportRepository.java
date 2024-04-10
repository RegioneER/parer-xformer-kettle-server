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

package it.eng.parer.kettle.server.persistence.lite.dao;

import it.eng.parer.kettle.lite.jpa.MonLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository per l'entity {@link MonLog}.
 *
 * @author Snidero_L
 */
@Repository
@Transactional(readOnly = true)
public interface ReportRepository extends JpaRepository<MonLog, Long> {

    @Override
    List<MonLog> findAll();

    /* https://docs.spring.io/spring-data/jpa/docs/1.5.0.RC1/reference/html/jpa.repositories.html */
    @Override
    public MonLog getOne(Long id);

    @Modifying
    @Transactional
    @Query("delete from MonLog l where l.idMonLog = ?1")
    public void eliminaLog(long ididMonLog);

    @Modifying
    @Transactional
    @Query("delete from MonLog l where l.idTrasf = ?1")
    public void eliminaTuttiLogDiTrasformazione(long idTrasf);

    @Modifying
    @Transactional
    @Override
    public MonLog save(MonLog entity);

    @Modifying
    @Transactional
    @Override
    public MonLog saveAndFlush(MonLog entity);

    public List<MonLog> findByIdTrasf(long idTrasf);

    public List<MonLog> findByIdTrasfAndCdLog(long idTrasf, String cdLog);
}
