package org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search;


import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.dto.JobMatchingsListDTO;

import java.util.List;

public interface JobMatchingsSearch {
    // 현재 근무지
    List<JobMatchingsListDTO> getCurrentWorkplaces(Long pno);

    // 이전 근무지
    List<JobMatchingsListDTO> getPastWorkplaces(Long pno);
}