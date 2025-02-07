package org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.QJobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsListDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;
import java.util.stream.Collectors;

public class JobMatchingsSearchImpl extends QuerydslRepositorySupport implements JobMatchingsSearch {

    public JobMatchingsSearchImpl() {
        super(JobMatchingsEntity.class);
    }

    @Override
    public List<JobMatchingsListDTO> getCurrentWorkplaces(Long pno) {
        QJobMatchingsEntity jobMatching = QJobMatchingsEntity.jobMatchingsEntity;

        List<JobMatchingsEntity> entities = from(jobMatching)
                .where(jobMatching.pno.pno.eq(pno)
                        .and(jobMatching.jmendDate.isNull())
                        .and(jobMatching.jmdelete.isFalse()))
                .orderBy(jobMatching.jmstartDate.desc())
                .fetch();

        return entities.stream()
                .map(entity -> JobMatchingsListDTO.builder()
                        .jmno(entity.getJmno())
                        .jpname(entity.getJpno().getJpname())
                        .jmregdate(entity.getJmregdate())
                        .jmstartDate(entity.getJmstartDate())
                        .jmendDate(entity.getJmendDate())
                        .jmhourlyRate(entity.getJmhourlyRate())
                        .jmworkDays(entity.getJmworkDays())
                        .jmworkStartTime(entity.getJmworkStartTime())
                        .jmworkEndTime(entity.getJmworkEndTime())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<JobMatchingsListDTO> getPastWorkplaces(Long pno) {
        QJobMatchingsEntity jobMatching = QJobMatchingsEntity.jobMatchingsEntity;

        List<JobMatchingsEntity> entities = from(jobMatching)
                .where(jobMatching.pno.pno.eq(pno)
                        .and(jobMatching.jmendDate.isNotNull())
                        .and(jobMatching.jmdelete.isFalse()))
                .orderBy(jobMatching.jmendDate.desc())
                .fetch();

        return entities.stream()
                .map(entity -> JobMatchingsListDTO.builder()
                        .jmno(entity.getJmno())
                        .jpname(entity.getJpno().getJpname())
                        .jmregdate(entity.getJmregdate())
                        .jmstartDate(entity.getJmstartDate())
                        .jmendDate(entity.getJmendDate())
                        .jmhourlyRate(entity.getJmhourlyRate())
                        .jmworkDays(entity.getJmworkDays())
                        .jmworkStartTime(entity.getJmworkStartTime())
                        .jmworkEndTime(entity.getJmworkEndTime())
                        .build())
                .collect(Collectors.toList());
    }
}
