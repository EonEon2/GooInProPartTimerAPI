package org.gooinpro.gooinproparttimerapi.jobpostings.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.dto.JobPostingsListDTO;
import org.gooinpro.gooinproparttimerapi.jobpostings.service.JobPostingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/part/api/v1/jobposting")
@Log4j2
@RequiredArgsConstructor
public class JobPostingsController {

    private final JobPostingsService jobPostingsService;

    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<JobPostingsListDTO>> joblist(PageRequestDTO pageRequestDTO) {
        log.info("job list");
        return ResponseEntity.ok(jobPostingsService.JobPostingsList(pageRequestDTO));
    }
}
