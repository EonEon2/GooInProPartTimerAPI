package org.gooinpro.gooinproparttimerapi.jobpostings.repository.search;

import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingDetailDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingsListDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;

import java.util.Optional;

public interface JobPostingsSearch {

    PageResponseDTO<JobPostingsListDTO> jobPostingsList(PageRequestDTO pageRequestDTO);

    JobPostingDetailDTO jobPostingDetail(Long jpno);



}
