package org.gooinpro.gooinproparttimerapi.jobmatchings.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsListDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsTimeDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsWorkListDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.repository.JobMatchingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class JobMatchingsLogService {

    private final JobMatchingsRepository jobMatchingsRepository;

    // 현재 근무지 목록 조회
    public List<JobMatchingsListDTO> getCurrentWorkplaces(Long pno) {
        return jobMatchingsRepository.getCurrentWorkplaces(pno);
    }

    // 이전 근무지 목록 조회
    public List<JobMatchingsListDTO> getPastWorkplaces(Long pno) {
        return jobMatchingsRepository.getPastWorkplaces(pno);
    }

<<<<<<< HEAD
    // 근무지 상세 정보 조회
    public JobMatchingsListDTO getJobsDetail(Long jmno) {
        log.info("근무지 상세 정보 service for jmno: {}", jmno);
        return jobMatchingsRepository.getJobsDetail(jmno);
    }

=======
    public JobMatchingsTimeDTO getWorkplaceTimes(Long pno, Long jpno) {
        return jobMatchingsRepository.getWorkplaceTimes(pno, jpno);
    }

    public PageResponseDTO<JobMatchingsWorkListDTO> getWorkPlaceList(PageRequestDTO pageRequestDTO, Long pno) {
        return jobMatchingsRepository.getWorkPlaceList(pageRequestDTO, pno);
    }
>>>>>>> ef4e30c2f2750be133c2bd3686a986f5e37912b2
}
