package org.gooinpro.gooinproparttimerapi.jobpostingsapplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

    private final WebClient webClient;

    @Value("${org.gooinpro.fcmurl}")
    private String fcmUrl;

    //Employer Token 가져와서 FCM Message 보내기
    private Mono<String> sendFCMMessage(FCMRequestDTO fcmRequestDTO) {

        String endPoint = "/send";

        String jsonRequest = null;
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            jsonRequest = objectMapper.writeValueAsString(fcmRequestDTO);

        } catch (Exception e) {

            log.error("Failed to convert fcmRequestDTO to JSON", e);
            throw new RuntimeException("Failed to convert fcmRequestDTO to JSON", e);
        }

        if (jsonRequest == null) {

            log.error("JSON request is null, aborting FCM message sending.");
            throw new RuntimeException("JSON request is null, aborting FCM message sending.");
        }

        return webClient.post()
                .uri(fcmUrl + endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(response -> log.info("FCM message sending complete. response: {}", response))
                .doOnError(error -> log.error("FCM message sending failed", error));
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

        sendFCMMessage(fcmRequestDTO).subscribe();

        return "success add job Postings application";

    }
}
