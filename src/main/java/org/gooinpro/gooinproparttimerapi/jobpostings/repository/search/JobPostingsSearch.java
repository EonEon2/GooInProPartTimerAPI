package org.gooinpro.gooinproparttimerapi.jobpostings.repository.search;

import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingsListDTO;

public interface JobPostingsSearch {

    PageResponseDTO<JobPostingsListDTO> jobPostingsList(PageRequestDTO pageRequestDTO);


}
