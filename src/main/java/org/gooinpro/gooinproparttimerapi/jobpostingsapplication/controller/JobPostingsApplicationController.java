package org.gooinpro.gooinproparttimerapi.jobpostingsapplication.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.dto.JobPostingsApplicationRegisterDTO;
import org.gooinpro.gooinproparttimerapi.jobpostingsapplication.service.JobPostingsApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/part/api/v1/jobpostingapplication")
@Log4j2
@RequiredArgsConstructor
public class JobPostingsApplicationController {

    private final JobPostingsApplicationService jobPostingsApplicationService;

    @PostMapping("/add")
    public ResponseEntity<String> JobPostingsApplicationAdd(@RequestBody JobPostingsApplicationRegisterDTO dto) {
        log.info("job postings application register");

        jobPostingsApplicationService.jobPostingsRegister(dto);

        return ResponseEntity.ok("success register");
    }
}
