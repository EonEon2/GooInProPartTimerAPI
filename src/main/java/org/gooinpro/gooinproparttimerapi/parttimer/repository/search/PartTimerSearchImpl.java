package org.gooinpro.gooinproparttimerapi.parttimer.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.QJobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.QJobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.QPartTimerEntity;
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
        QPartTimerEntity partTimerEntity = QPartTimerEntity.partTimerEntity;
        QJobMatchingsEntity jobMatchingsEntity = QJobMatchingsEntity.jobMatchingsEntity;
        // QWorkLogEntity 필요.......
        return null;
    }

    @Override
    public List<PartTimerWorkPlaceDTO> getPreviousWorkplaces(Long pno) {
        QPartTimerEntity partTimerEntity = QPartTimerEntity.partTimerEntity;
        QJobMatchingsEntity jobMatchingsEntity = QJobMatchingsEntity.jobMatchingsEntity;
        QJobPostingsEntity jobPostingsEntity = QJobPostingsEntity.jobPostingsEntity;

        JPQLQuery<PartTimerWorkPlaceDTO> query = from(partTimerEntity)
                .leftJoin(jobMatchingsEntity).on(jobMatchingsEntity.pno.pno.eq(partTimerEntity.pno))
                .leftJoin(jobPostingsEntity).on(jobPostingsEntity.jpno.eq(jobMatchingsEntity.jpno.jpno))
                .where(partTimerEntity.pno.eq(pno))
                .select(Projections.constructor(
                        PartTimerWorkPlaceDTO.class,
                        jobMatchingsEntity.jmno,
                        jobPostingsEntity.jpname,
                        jobMatchingsEntity.jmregdate,
                        jobMatchingsEntity.jmstartDate,
                        jobMatchingsEntity.jmendDate,
                        jobMatchingsEntity.jmhourlyRate,
                        jobMatchingsEntity.jmworkDays,
                        jobMatchingsEntity.jmworkStartTime,
                        jobMatchingsEntity.jmworkEndTime
                ));

        return query.fetch();
    }
}