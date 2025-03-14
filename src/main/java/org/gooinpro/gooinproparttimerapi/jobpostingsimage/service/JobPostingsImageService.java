package org.gooinpro.gooinproparttimerapi.jobpostingsimage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobpostingsimage.dto.JobPostingsImageDetailDTO;
import org.gooinpro.gooinproparttimerapi.jobpostingsimage.repository.JobPostingsImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class JobPostingsImageService {

    private final JobPostingsImageRepository jobPostingsImageRepository;

    public JobPostingsImageDetailDTO getJobPostingsImage(Long jpno){

        log.info("jobpostings image service");

        return jobPostingsImageRepository.getJobPostingsImage(jpno);
    }
}
