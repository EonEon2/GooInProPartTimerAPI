package org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search;

<<<<<<< HEAD
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.QJobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsListDTO;
import org.gooinpro.gooinproparttimerapi.workplace.domain.WorkPlaceEntity;
=======
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
>>>>>>> ef4e30c2f2750be133c2bd3686a986f5e37912b2
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.access.method.P;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class JobMatchingsSearchImpl extends QuerydslRepositorySupport implements JobMatchingsSearch {

    public JobMatchingsSearchImpl() {
        super(JobMatchingsEntity.class);
    }

    @Override
    public List<JobMatchingsListDTO> getCurrentWorkplaces(Long pno) {

        QJobMatchingsEntity jobMatching = QJobMatchingsEntity.jobMatchingsEntity;

        log.info("Fetching current workplaces for pno: {}", pno);

        // 현재 근무중인 근무지 조회 (종료일이 없고, 삭제되지 않은 데이터)
        List<JobMatchingsEntity> entities = from(jobMatching)
                .where(jobMatching.pno.pno.eq(pno)
                        .and(jobMatching.jmendDate.isNull())
                        .and(jobMatching.jmdelete.isFalse()))
                .orderBy(jobMatching.jmstartDate.desc())  // 시작일 기준 내림차순
                .fetch();

        log.info("Found {} current workplace entities", entities.size());

        return entities.stream()
                .map(entity -> {
                    // 각 엔티티의 상세 정보 로깅
                    log.info("Processing entity - jmno: {}, jpname: {}", entity.getJmno(), entity.getJpno().getJpname());

                    // WorkPlace 정보 로깅
                    String roadAddress = Optional.ofNullable(entity.getJpno().getWorkPlace())
                            .map(WorkPlaceEntity::getWroadAddress)
                            .orElse(null);
                    String detailAddress = Optional.ofNullable(entity.getJpno().getWorkPlace())
                            .map(WorkPlaceEntity::getWdetailAddress)
                            .orElse(null);

                    log.info("Workplace info - roadAddress: {}, detailAddress: {}", roadAddress, detailAddress);

                    return JobMatchingsListDTO.builder()
                            .jmno(entity.getJmno())
                            .jpname(entity.getJpno().getJpname())
                            .jmregdate(entity.getJmregdate())
                            .jmstartDate(entity.getJmstartDate())
                            .jmendDate(entity.getJmendDate())
                            .jmhourlyRate(entity.getJmhourlyRate())
                            .jmworkDays(entity.getJmworkDays())
                            .jmworkStartTime(entity.getJmworkStartTime())
                            .jmworkEndTime(entity.getJmworkEndTime())
                            .wroadAddress(roadAddress)
                            .wdetailAddress(detailAddress)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<JobMatchingsListDTO> getPastWorkplaces(Long pno) {

        QJobMatchingsEntity jobMatching = QJobMatchingsEntity.jobMatchingsEntity;

        // 과거 근무지 조회 (종료일이 있고, 삭제되지 않은 데이터)
        List<JobMatchingsEntity> entities = from(jobMatching)
                .where(jobMatching.pno.pno.eq(pno)
                        .and(jobMatching.jmendDate.isNotNull())
                        .and(jobMatching.jmdelete.isFalse()))
                .orderBy(jobMatching.jmendDate.desc())  // 종료일 기준 내림차순
                .fetch();

        return entities.stream()
                .map(entity -> JobMatchingsListDTO.builder()
                        // 기본 정보
                        .jmno(entity.getJmno())
                        .jpname(entity.getJpno().getJpname())
                        .jmregdate(entity.getJmregdate())
                        .jmstartDate(entity.getJmstartDate())
                        .jmendDate(entity.getJmendDate())
                        .jmhourlyRate(entity.getJmhourlyRate())
                        .jmworkDays(entity.getJmworkDays())
                        .jmworkStartTime(entity.getJmworkStartTime())
                        .jmworkEndTime(entity.getJmworkEndTime())
                        // 근무지 주소 정보 추가
                        .wroadAddress(Optional.ofNullable(entity.getJpno().getWorkPlace())
                                .map(WorkPlaceEntity::getWroadAddress)
                                .orElse(null))
                        .wdetailAddress(Optional.ofNullable(entity.getJpno().getWorkPlace())
                                .map(WorkPlaceEntity::getWdetailAddress)
                                .orElse(null))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
<<<<<<< HEAD
    public JobMatchingsListDTO getJobsDetail(Long jmno) {
        QJobMatchingsEntity jobMatching = QJobMatchingsEntity.jobMatchingsEntity;

        JobMatchingsEntity entity = from(jobMatching)
                .where(jobMatching.jmno.eq(jmno))
                .fetchOne();

        if (entity == null) {
            throw new RuntimeException("근무지 정보를 찾을 수 없습니다: " + jmno);
        }

        log.info("Found job matching detail - jmno: {}, jpname: {}",
                entity.getJmno(), entity.getJpno().getJpname());

        String roadAddress = Optional.ofNullable(entity.getJpno().getWorkPlace())
                .map(WorkPlaceEntity::getWroadAddress)
                .orElse(null);
        String detailAddress = Optional.ofNullable(entity.getJpno().getWorkPlace())
                .map(WorkPlaceEntity::getWdetailAddress)
                .orElse(null);

        log.info("Workplace info - roadAddress: {}, detailAddress: {}",
                roadAddress, detailAddress);

        return JobMatchingsListDTO.builder()
                .jmno(entity.getJmno())
                .jpname(entity.getJpno().getJpname())
                .jmregdate(entity.getJmregdate())
                .jmstartDate(entity.getJmstartDate())
                .jmendDate(entity.getJmendDate())
                .jmhourlyRate(entity.getJmhourlyRate())
                .jmworkDays(entity.getJmworkDays())
                .jmworkStartTime(entity.getJmworkStartTime())
                .jmworkEndTime(entity.getJmworkEndTime())
                .wroadAddress(roadAddress)
                .wdetailAddress(detailAddress)
=======
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
>>>>>>> ef4e30c2f2750be133c2bd3686a986f5e37912b2
                .build();
    }
}
