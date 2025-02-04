package org.gooinpro.gooinproparttimerapi.jobpostings.repository.search;

import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class JobPostingsSearchImpl extends QuerydslRepositorySupport implements JobPostingsSearch {

    public JobPostingsSearchImpl() {
        super(JobPostingsEntity.class);
    }
}
