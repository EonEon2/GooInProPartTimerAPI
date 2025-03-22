package org.gooinpro.gooinproparttimerapi.jobpostingsapplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.repository.JobPostingsRepository;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.domain.JobPostingsApplicationEntity;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.dto.FCMRequestDTO;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.dto.JobPostingsApplicationRegisterDTO;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.repository.JobPostingsApplicationRepository;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class JobPostingsApplicationService {

    private final JobPostingsApplicationRepository jobPostingsApplicationRepository;
    private final PartTimerRepository partTimerRepository;
    private final JobPostingsRepository jobPostingsRepository;

    private final RestTemplate restTemplate;

    @Value("${org.gooinpro.fcmurl}")
    private String fcmUrl;

    //Employer Token 가져와서 FCM Message 보내기
    private void sendFCMMessage(FCMRequestDTO fcmRequestDTO) {

        String endPoint = "/send";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonRequest = null;
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            jsonRequest = objectMapper.writeValueAsString(fcmRequestDTO);

            log.info("................................");
            log.info(jsonRequest);
        } catch (Exception e) {

            log.error("Failed to convert fcmRequestDTO to JSON", e);
            return;  // 예외 발생 시 메소드 종료
        }

        if (jsonRequest == null) {

            log.error("JSON request is null, aborting FCM message sending.");
            return;  // JSON 변환에 실패했으면 더 이상 진행하지 않음
        }

        HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);

        ResponseEntity<String> response = null;

        try {

            response = restTemplate.exchange(fcmUrl + endPoint, HttpMethod.POST, request, String.class);
        } catch (Exception e) {

            log.error("Failed to send FCM Message", e);
        }

        log.info(response);
    }


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

        FCMRequestDTO fcmRequestDTO = new FCMRequestDTO();

        fcmRequestDTO.setToken(Collections.singletonList(jpno.get().getEmployer().getEtoken()));
        fcmRequestDTO.setTitle(jpno.get().getJpname() + " 지원");
        fcmRequestDTO.setContent(dto.getJpacontent());

        sendFCMMessage(fcmRequestDTO);

        return "success add job Postings application";

    }
}
