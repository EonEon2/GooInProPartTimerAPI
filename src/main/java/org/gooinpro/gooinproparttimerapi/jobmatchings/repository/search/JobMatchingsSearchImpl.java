package org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search;

import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class JobMatchingsSearchImpl extends QuerydslRepositorySupport implements JobMatchingsSearch {

    public JobMatchingsSearchImpl() {
        super(JobMatchingsEntity.class);
    }
}
