package org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.QJobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsListDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsTimeDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsWorkListDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.QJobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingDetailDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingsListDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.QPartTimerEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.access.method.P;

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

    @Override
    public JobMatchingsTimeDTO getWorkplaceTimes(Long pno, Long jpno) {

        QJobMatchingsEntity jobMatching = QJobMatchingsEntity.jobMatchingsEntity;

        JPQLQuery<JobMatchingsEntity> query = from(jobMatching);

        query.where(jobMatching.pno.pno.eq(pno));
        query.where(jobMatching.jpno.jpno.eq(jpno));

        JPQLQuery<JobMatchingsTimeDTO> tupleQuery = query.select(
                Projections.bean(JobMatchingsTimeDTO.class,
                        jobMatching.pno.pno,
                        jobMatching.jpno.jpno,
                        jobMatching.jmworkStartTime,
                        jobMatching.jmworkEndTime
                        )
        );

        JobMatchingsTimeDTO list = tupleQuery.fetchFirst();

        return list;
    }

    @Override
    public PageResponseDTO<JobMatchingsWorkListDTO> getWorkPlaceList(PageRequestDTO pageRequestDTO, Long pno) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize());

        QJobMatchingsEntity jobMatching = QJobMatchingsEntity.jobMatchingsEntity;
        QJobPostingsEntity jobPostings = QJobPostingsEntity.jobPostingsEntity;

        JPQLQuery<JobMatchingsEntity> query = from(jobMatching);

        query.leftJoin(jobPostings).on(jobPostings.jpno.eq(jobMatching.jpno.jpno));

        query.where(jobMatching.pno.pno.eq(pno));
        query.where(jobMatching.jmdelete.isFalse());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<JobMatchingsWorkListDTO> tupleQuery = query.select(
                Projections.bean(JobMatchingsWorkListDTO.class,
                        jobMatching.jmno,
                        jobMatching.pno.pno,
                        jobMatching.jpno.jpno,
                        jobMatching.jpno.jpname,
                        jobMatching.jpno.jpworkDays
                )
        );

        List<JobMatchingsWorkListDTO> dtoList = tupleQuery.fetch();

        long total = tupleQuery.fetchCount();

        return PageResponseDTO.<JobMatchingsWorkListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();
    }
}
