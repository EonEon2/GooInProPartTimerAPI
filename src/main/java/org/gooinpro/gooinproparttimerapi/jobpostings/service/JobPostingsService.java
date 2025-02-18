package org.gooinpro.gooinproparttimerapi.jobpostings.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingDetailDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingsListDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.repository.JobPostingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class JobPostingsService {

    private final JobPostingsRepository jobPostingsRepository;

    public PageResponseDTO<JobPostingsListDTO> JobPostingsList(PageRequestDTO pageRequestDTO) {

        return jobPostingsRepository.jobPostingsList(pageRequestDTO);

    }

    public JobPostingDetailDTO JobPostingDetail(Long jpno) {

        return jobPostingsRepository.jobPostingDetail(jpno);
    }
}
