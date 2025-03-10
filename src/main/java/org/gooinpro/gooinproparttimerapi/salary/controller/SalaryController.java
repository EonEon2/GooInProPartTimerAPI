package org.gooinpro.gooinproparttimerapi.salary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.salary.dto.SalaryDailyDTO;
import org.gooinpro.gooinproparttimerapi.salary.dto.SalaryJobDTO;
import org.gooinpro.gooinproparttimerapi.salary.dto.SalaryMonthlyDTO;
import org.gooinpro.gooinproparttimerapi.salary.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/part/api/v1/salary")
@RequiredArgsConstructor
@Log4j2
public class SalaryController {

    private final SalaryService salaryService;

    // 월별 급여 조회
    @GetMapping("/month")
    public ResponseEntity<List<SalaryMonthlyDTO>> getMonthlySalary(
            @RequestParam Long pno,
            @RequestParam(required = false) Integer year) {
        log.info("월별 급여 조회 controller for pno: {}, year: {}", pno, year);
        return ResponseEntity.ok(salaryService.getMonthlySalary(pno, year));
    }

    // 알바별 급여 조회
    @GetMapping("/jobs")
    public ResponseEntity<List<SalaryJobDTO>> getSalaryByJobs(@RequestParam Long pno) {
        log.info("알바별 급여 조회 controller for pno: {}", pno);
        return ResponseEntity.ok(salaryService.getSalaryByJobs(pno));
    }

    // 일별 급여 조회
    @GetMapping("/daily")
    public ResponseEntity<List<SalaryDailyDTO>> getDailySalary(
            @RequestParam Long pno,
            @RequestParam(required = false) Long jmno,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        log.info("일별 급여 조회 controller for pno: {}, jmno: {}, year: {}, month: {}", pno, jmno, year, month);
        return ResponseEntity.ok(salaryService.getDailySalary(pno, jmno, year, month));
    }
}
