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

package it.eng.parer.kettle.server.persistence.dao;

import it.eng.parer.kettle.jpa.MonExecTrasf;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository per l'entity {@link MonExecTrasf}.
 *
 * @author Snidero_L
 */
@Repository
@Transactional(readOnly = true)
public interface MonitoraggioRepository extends JpaRepository<MonExecTrasf, Long> {

    @Override
    List<MonExecTrasf> findAll();

    /* https://docs.spring.io/spring-data/jpa/docs/1.5.0.RC1/reference/html/jpa.repositories.html */
    MonExecTrasf findByIdPigObjectAndNmKsInstance(Long idPigObject, String nmKsInstance);

    @Override
    public MonExecTrasf getOne(Long id);

    @Modifying
    @Transactional
    @Query("delete from MonExecTrasf m where m.idPigObject = ?1 and m.nmKsInstance = ?2")
    public void pulisciTrasformazione(Long idPigObject, String nmKsInstance);

    @Modifying
    @Transactional
    @Override
    public MonExecTrasf save(MonExecTrasf entity);

    List<MonExecTrasf> findByNmKsInstanceAndDtInizioTrasfIsNotNullAndDtFineTrasfIsNull(String nmKsInstance);

    List<MonExecTrasf> findByNmKsInstanceAndTiStatoTrasf(String nmKsInstance,
            MonExecTrasf.STATO_TRASFORMAZIONE tiStatoTrasf);

    public long countByNmKsInstanceAndTiStatoTrasfIn(String nmKsInstance,
            MonExecTrasf.STATO_TRASFORMAZIONE... tiStatoTrasf);

    public Slice<MonExecTrasf> findByNmKsInstanceAndDtInizioTrasfBetweenAndTiStatoTrasfIn(Pageable pageable,
            String nmKsInstance, Date startDate, Date endDate, MonExecTrasf.STATO_TRASFORMAZIONE... tiStatoTrasf);

}
