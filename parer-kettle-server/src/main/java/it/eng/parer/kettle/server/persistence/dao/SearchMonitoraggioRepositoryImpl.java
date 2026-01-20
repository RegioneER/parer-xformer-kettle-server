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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Cappelli_F <Francesco.Cappelli@Regione.Emilia-Romagna.it>
 */
public class SearchMonitoraggioRepositoryImpl implements SearchMonitoraggioRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<MonExecTrasf> searchMonitoraggio(Long idObject, String transformationName, Date startDate, Date endDate,
            String nmKsInstance, int numResults, MonExecTrasf.STATO_TRASFORMAZIONE... tiStatoTrasf) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MonExecTrasf> cq = cb.createQuery(MonExecTrasf.class);

        Root<MonExecTrasf> met = cq.from(MonExecTrasf.class);

        cq.orderBy(cb.asc(met.get("dtInizioTrasf")));

        List<Predicate> predicates = new ArrayList<>();

        if (idObject != null) {
            predicates.add(cb.equal(met.get("idPigObject"), idObject));
        }

        if (transformationName != null && !transformationName.isEmpty()) {
            predicates.add(cb.like(cb.lower(met.get("nmTrasf")), cb.lower(cb.literal("%" + transformationName + "%"))));
        }

        predicates.add(cb.between(met.get("dtInizioTrasf"), startDate, endDate));
        predicates.add(cb.equal(met.get("nmKsInstance"), nmKsInstance));

        predicates.add(met.get("tiStatoTrasf").in(Arrays.asList(tiStatoTrasf)));

        Predicate[] predicatesArray = predicates.toArray(new Predicate[0]);
        cq.where(cb.and(predicatesArray));

        TypedQuery<MonExecTrasf> query = em.createQuery(cq).setMaxResults(numResults);
        return query.getResultList();
    }

}
