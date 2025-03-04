package org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search;


import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsListDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsTimeDTO;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsWorkListDTO;

import java.util.List;

public interface JobMatchingsSearch {
    // 현재 근무지
    List<JobMatchingsListDTO> getCurrentWorkplaces(Long pno);

    // 이전 근무지
    List<JobMatchingsListDTO> getPastWorkplaces(Long pno);

<<<<<<< HEAD
    // 근무지 상세 정보 조회
    JobMatchingsListDTO getJobsDetail(Long jmno);
=======
    JobMatchingsTimeDTO getWorkplaceTimes(Long pno, Long jpno);

    PageResponseDTO<JobMatchingsWorkListDTO> getWorkPlaceList(PageRequestDTO pageRequestDTO, Long pno);


>>>>>>> ef4e30c2f2750be133c2bd3686a986f5e37912b2
}