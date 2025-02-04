package org.gooinpro.gooinproparttimerapi.parttimer.repository.search;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PartTimerSearchImpl extends QuerydslRepositorySupport implements PartTimerSearch {

    public PartTimerSearchImpl() {
        super(PartTimerSearch.class);
    }

}
