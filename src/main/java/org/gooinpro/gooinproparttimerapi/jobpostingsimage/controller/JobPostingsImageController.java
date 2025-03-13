package org.gooinpro.gooinproparttimerapi.jobpostingsimage.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobpostingsimage.dto.JobPostingsImageDetailDTO;
import org.gooinpro.gooinproparttimerapi.jobpostingsimage.service.JobPostingsImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/part/api/v1/jobPostingsImage")
@Log4j2
@RequiredArgsConstructor
public class JobPostingsImageController {

    private final JobPostingsImageService jobPostingsImageService;

    @GetMapping("/get")
    public ResponseEntity<JobPostingsImageDetailDTO> getJobPostingsImage(@RequestParam Long jpno) {
        log.info("job postings image controller");

        return ResponseEntity.ok(jobPostingsImageService.getJobPostingsImage(jpno));
    }
}
