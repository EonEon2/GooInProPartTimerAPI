package org.gooinpro.gooinproparttimerapi.jobpostingsapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.repository.JobPostingsRepository;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.domain.JobPostingsApplicationEntity;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.dto.JobPostingsApplicationRegisterDTO;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.repository.JobPostingsApplicationRepository;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class JobPostingsApplicationService {

    private final JobPostingsApplicationRepository jobPostingsApplicationRepository;
    private final PartTimerRepository partTimerRepository;
    private final JobPostingsRepository jobPostingsRepository;

    public String jobPostingsRegister(JobPostingsApplicationRegisterDTO dto) {

        Optional<PartTimerEntity> pno = partTimerRepository.findByPno(dto.getPno());
        Optional<JobPostingsEntity> jpno = jobPostingsRepository.findByJpno(dto.getJpno());

        JobPostingsApplicationEntity jobPostingsApplicationEntity = JobPostingsApplicationEntity.builder()
                .jpano(dto.getJpano())
                .jpacontent(dto.getJpacontent())
                .jpahourlyRate(dto.getJpahourlyRate())
                .jpadelete(false)
                .partTimer(pno.get())
                .jobPostings(jpno.get())
                .build();

        jobPostingsApplicationRepository.save(jobPostingsApplicationEntity);

        return "success add job Postings application";

    }
}
