package org.gooinpro.gooinproparttimerapi.parttimer.repository.search;

import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerWorkHistoryDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerWorkPlaceDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import java.util.List;

@Log4j2
public class PartTimerSearchImpl extends QuerydslRepositorySupport implements PartTimerSearch {

    public PartTimerSearchImpl() {
        super(PartTimerEntity.class);
    }

    @Override
    public List<PartTimerWorkHistoryDTO> getWorkHistory(Long pno) {
        //
        return null;
    }

    @Override
    public List<PartTimerWorkPlaceDTO> getPreviousWorkplaces(Long pno) {
        //
        return null;
    }
}