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

    JobMatchingsTimeDTO getWorkplaceTimes(Long pno, Long jpno);

    PageResponseDTO<JobMatchingsWorkListDTO> getWorkPlaceList(PageRequestDTO pageRequestDTO, Long pno);


}