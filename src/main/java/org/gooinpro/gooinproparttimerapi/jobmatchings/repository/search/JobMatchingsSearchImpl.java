package org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search;

import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.QJobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsListDTO;
import org.gooinpro.gooinproparttimerapi.workplace.domain.WorkPlaceEntity;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
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
                .build();
    }
}
