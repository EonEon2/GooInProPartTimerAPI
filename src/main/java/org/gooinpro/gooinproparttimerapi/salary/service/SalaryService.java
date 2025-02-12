package org.gooinpro.gooinproparttimerapi.salary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.salary.dto.SalaryJobDTO;
import org.gooinpro.gooinproparttimerapi.salary.dto.SalaryMonthlyDTO;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.WorkLogsDTO;
import org.gooinpro.gooinproparttimerapi.worklogs.repository.WorkLogsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class SalaryService {

    private final WorkLogsRepository workLogsRepository;

    // 월별 급여 조회 (PA-012)
    public List<SalaryMonthlyDTO> getMonthlySalary(Long pno, Integer year) {
        log.info("Getting monthly salary for pno: {}, year: {}", pno, year);

        List<WorkLogsDTO> monthlyLogs = workLogsRepository.getMonthlyWorkLogs(pno, year, null);
        if (monthlyLogs.isEmpty()) {
            log.info("No work logs found for pno: {}", pno);
            return Collections.emptyList();
        }

        // 월별로 그룹화하여 급여 계산
        Map<Integer, List<WorkLogsDTO>> monthlyGroups = new HashMap<>();
        for (WorkLogsDTO log : monthlyLogs) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(log.getWlstartTime().getTime());
            int month = cal.get(Calendar.MONTH) + 1;

            monthlyGroups.computeIfAbsent(month, k -> new ArrayList<>()).add(log);
        }

        List<SalaryMonthlyDTO> result = new ArrayList<>();
        for (Map.Entry<Integer, List<WorkLogsDTO>> entry : monthlyGroups.entrySet()) {
            List<WorkLogsDTO> logsForMonth = entry.getValue();
            int totalHours = calculateTotalHours(logsForMonth);
            int totalSalary = calculateTotalSalary(logsForMonth);

            result.add(SalaryMonthlyDTO.builder()
                    .year(year)
                    .month(entry.getKey())
                    .totalHours(totalHours)
                    .totalSalary(totalSalary)
                    .jpname(logsForMonth.get(0).getJpname())
                    .build());
        }

        return result;
    }

    // 알바별 급여 조회 (PA-013)
    public List<SalaryJobDTO> getSalaryByJobs(Long pno) {
        log.info("Getting salary by jobs for pno: {}", pno);

        List<WorkLogsDTO> allLogs = workLogsRepository.getMonthlyWorkLogs(pno, null, null);
        if (allLogs.isEmpty()) {
            log.info("No work logs found for pno: {}", pno);
            return Collections.emptyList();
        }

        Map<Long, List<WorkLogsDTO>> jobGroups = new HashMap<>();
        for (WorkLogsDTO log : allLogs) {
            jobGroups.computeIfAbsent(log.getJmno(), k -> new ArrayList<>()).add(log);
        }

        List<SalaryJobDTO> result = new ArrayList<>();
        for (List<WorkLogsDTO> logsForJob : jobGroups.values()) {
            int totalHours = calculateTotalHours(logsForJob);
            int totalSalary = calculateTotalSalary(logsForJob);

            result.add(SalaryJobDTO.builder()
                    .jpname(logsForJob.get(0).getJpname())
                    .totalHours(totalHours)
                    .totalSalary(totalSalary)
                    .startDate(getEarliestDate(logsForJob))
                    .endDate(getLatestDate(logsForJob))
                    .build());
        }

        return result;
    }

    // 총 근무시간 계산
    private int calculateTotalHours(List<WorkLogsDTO> logs) {
        int totalHours = 0;
        for (WorkLogsDTO log : logs) {
            long diffMillis = log.getWlendTime().getTime() - log.getWlstartTime().getTime();
            totalHours += diffMillis / (60 * 60 * 1000); // 밀리초를 시간으로 변환
        }
        return totalHours;
    }

    // 총 급여 계산
    private int calculateTotalSalary(List<WorkLogsDTO> logs) {
        int totalSalary = 0;
        for (WorkLogsDTO log : logs) {
            long diffMillis = log.getWlendTime().getTime() - log.getWlstartTime().getTime();
            int hours = (int) (diffMillis / (60 * 60 * 1000));
            totalSalary += hours * log.getJmhourlyRate();  // hourlyRate -> jmhourlyRate로 변경
        }
        return totalSalary;
    }

    private Timestamp getEarliestDate(List<WorkLogsDTO> logs) {
        Timestamp earliest = logs.get(0).getWlstartTime();
        for (WorkLogsDTO log : logs) {
            if (log.getWlstartTime().before(earliest)) {
                earliest = log.getWlstartTime();
            }
        }
        return earliest;
    }

    private Timestamp getLatestDate(List<WorkLogsDTO> logs) {
        Timestamp latest = logs.get(0).getWlendTime();
        for (WorkLogsDTO log : logs) {
            if (log.getWlendTime().after(latest)) {
                latest = log.getWlendTime();
            }
        }
        return latest;
    }
}
