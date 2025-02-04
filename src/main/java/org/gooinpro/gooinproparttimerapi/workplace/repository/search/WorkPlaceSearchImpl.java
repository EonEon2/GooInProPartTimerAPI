package org.gooinpro.gooinproparttimerapi.workplace.repository.search;

import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.workplace.domain.WorkPlaceEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class WorkPlaceSearchImpl extends QuerydslRepositorySupport implements WorkPlaceSearch {

    public WorkPlaceSearchImpl() {
        super(WorkPlaceEntity.class);
    }
}
