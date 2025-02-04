package org.gooinpro.gooinproparttimerapi.employer.repository.search;

import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class EmployerSearchImpl extends QuerydslRepositorySupport implements EmployerSearch {

    public EmployerSearchImpl() {
        super(EmployerEntity.class);
    }
}
