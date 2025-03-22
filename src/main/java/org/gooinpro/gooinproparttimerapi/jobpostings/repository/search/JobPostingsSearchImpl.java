package org.gooinpro.gooinproparttimerapi.jobpostings.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.employer.domain.QEmployerEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.QJobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingDetailDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingsListDTO;
import org.gooinpro.gooinproparttimerapi.workplace.domain.QWorkPlaceEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class JobPostingsSearchImpl extends QuerydslRepositorySupport implements JobPostingsSearch {

    public JobPostingsSearchImpl() {
        super(JobPostingsEntity.class);
    }

    @Override
    public PageResponseDTO<JobPostingsListDTO> jobPostingsList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(
                pageRequestDTO.getPage() - 1,
                pageRequestDTO.getSize());

        QJobPostingsEntity jobPostings = QJobPostingsEntity.jobPostingsEntity;
        QEmployerEntity employer = QEmployerEntity.employerEntity;
        QWorkPlaceEntity workPlace = QWorkPlaceEntity.workPlaceEntity;

        JPQLQuery<JobPostingsEntity> query = from(jobPostings);

        query.leftJoin(employer).on(employer.eq(jobPostings.employer));
        query.leftJoin(workPlace).on(workPlace.eq(jobPostings.workPlace));

        query.where(jobPostings.jpdelete.isFalse());

        this.getQuerydsl().applyPagination(pageable, query);

        JPQLQuery<JobPostingsListDTO> tupleQuery = query.select(
                Projections.bean(JobPostingsListDTO.class,
                        jobPostings.jpno,
                        jobPostings.workPlace.wpno,
                        jobPostings.jpname,
                        jobPostings.workPlace.wroadAddress,
                        jobPostings.workPlace.wdetailAddress,
                        jobPostings.jphourlyRate,
                        jobPostings.jpenddate,
                        jobPostings.workPlace.wlati,
                        jobPostings.workPlace.wlong)
        );

        List<JobPostingsListDTO> dtoList = tupleQuery.fetch();

        long total = query.fetchCount();

        return PageResponseDTO.<JobPostingsListDTO>withAll()
                .dtoList(dtoList)
                .totalCount(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

    }

    @Override
    public JobPostingDetailDTO jobPostingDetail(Long jpno) {

        QJobPostingsEntity jobPostings = QJobPostingsEntity.jobPostingsEntity;
        QWorkPlaceEntity workPlace = QWorkPlaceEntity.workPlaceEntity;

        JPQLQuery<JobPostingsEntity> query = from(jobPostings);

        query.leftJoin(workPlace).on(workPlace.eq(jobPostings.workPlace));

        query.where(jobPostings.jpno.eq(jpno));

        JPQLQuery<JobPostingDetailDTO> tupleQuery = query.select(
                Projections.bean(JobPostingDetailDTO.class,
                        jobPostings.jpno,
                        jobPostings.jpname,
                        jobPostings.workPlace.wroadAddress,
                        jobPostings.workPlace.wdetailAddress,
                        jobPostings.jphourlyRate,
                        jobPostings.jpworkDays,
                        jobPostings.jpworkStartTime,
                        jobPostings.jpworkEndTime,
                        jobPostings.employer.eno.as("eno")
                )
        );

        JobPostingDetailDTO list = tupleQuery.fetchFirst();

        return list;
    }
}


