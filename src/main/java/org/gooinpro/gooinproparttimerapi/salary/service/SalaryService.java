package org.gooinpro.gooinproparttimerapi.salary.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.salary.dto.SalaryDailyDTO;
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

    // 일별 급여 조회
    public List<SalaryDailyDTO> getDailySalary(Long pno, Long jmno, Integer year, Integer month) {
        log.info("Getting daily salary for pno: {}, jmno: {}, year: {}, month: {}", pno, jmno, year, month);

        List<WorkLogsDTO> workLogs;
        if (jmno != null) {
            // 특정 근무지의 근무 기록만 조회
            workLogs = workLogsRepository.getWorkLogsByJob(pno, jmno);
            if (year != null) {
                // 연도 필터링
                workLogs = workLogs.stream()
                        .filter(log -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(log.getWlstartTime().getTime());
                            return cal.get(Calendar.YEAR) == year;
                        })
                        .toList();
            }
            if (month != null) {
                // 월 필터링
                workLogs = workLogs.stream()
                        .filter(log -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(log.getWlstartTime().getTime());
                            return cal.get(Calendar.MONTH) + 1 == month;
                        })
                        .toList();
            }
        } else {
            // 모든 근무지의 근무 기록 조회
            workLogs = workLogsRepository.getMonthlyWorkLogs(pno, year, month);
        }

        if (workLogs.isEmpty()) {
            log.info("No work logs found for pno: {}, jmno: {}", pno, jmno);
            return Collections.emptyList();
        }

        // 결근이 아닌 근무 기록만 필터링 (추가된 부분)
        workLogs = workLogs.stream()
                .filter(log -> log.getWlworkStatus() != 3)
                .toList();

        if (workLogs.isEmpty()) {
            log.info("No valid work logs found for pno: {}, jmno: {}", pno, jmno);
            return Collections.emptyList();
        }

        // 일별로 그룹화하여 급여 계산
        Map<String, List<WorkLogsDTO>> dailyGroups = new HashMap<>();
        for (WorkLogsDTO log : workLogs) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(log.getWlstartTime().getTime());
            // 날짜만 추출 (년-월-일 형식)
            String dateKey = String.format("%d-%02d-%02d",
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH));

            dailyGroups.computeIfAbsent(dateKey, k -> new ArrayList<>()).add(log);
        }

        List<SalaryDailyDTO> result = new ArrayList<>();
        for (Map.Entry<String, List<WorkLogsDTO>> entry : dailyGroups.entrySet()) {
            List<WorkLogsDTO> logsForDay = entry.getValue();
            int dailyHours = calculateTotalHours(logsForDay);
            int dailySalary = calculateTotalSalary(logsForDay);

            // 날짜 문자열을 Timestamp로 변환
            String[] dateParts = entry.getKey().split("-");
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(dateParts[0]),
                    Integer.parseInt(dateParts[1]) - 1,
                    Integer.parseInt(dateParts[2]),
                    0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Timestamp workDate = new Timestamp(cal.getTimeInMillis());

            result.add(SalaryDailyDTO.builder()
                    .workDate(workDate)
                    .hours(dailyHours)
                    .salary(dailySalary)
                    .jpname(logsForDay.get(0).getJpname())
                    .jmno(logsForDay.get(0).getJmno())
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
        for (WorkLogsDTO logItem : logs) {
            // 결근인 경우 급여 계산에서 제외
            if (logItem.getWlworkStatus() == 3) {
                log.info("Skipping salary calculation for absent work: {}", logItem.getWlno());
                continue;
            }

            Timestamp startTime = logItem.getWlstartTime();
            Timestamp endTime = logItem.getWlendTime();

            // 지각인 경우 변경된 출근 시간 사용
            if (logItem.getWlworkStatus() == 1 && logItem.getWlchangedStartTime() != null) {
                // 변경된 출근 시간이 있는 경우 사용
                // 참고: wlchangedStartTime이 String 타입이라면 Timestamp로 변환 필요
                // 여기서는 간단히 로그만 추가
                log.info("Using changed start time for late work: {}", logItem.getWlchangedStartTime());
            }

            // 조퇴인 경우 변경된 퇴근 시간 사용
            if (logItem.getWlworkStatus() == 2 && logItem.getWlchangedEndTime() != null) {
                // 변경된 퇴근 시간이 있는 경우 사용
                // 참고: wlchangedEndTime이 String 타입이라면 Timestamp로 변환 필요
                // 여기서는 간단히 로그만 추가
                log.info("Using changed end time for early leave: {}", logItem.getWlchangedEndTime());
            }

            long diffMillis = endTime.getTime() - startTime.getTime();
            int hours = (int) (diffMillis / (60 * 60 * 1000)); // 밀리초를 시간으로 변환

            log.info("Calculating salary for jpname: {}, hourlyRate: {}, startTime: {}, endTime: {}, status: {}",
                    logItem.getJpname(),
                    logItem.getJmhourlyRate(),
                    startTime,
                    endTime,
                    logItem.getWlworkStatus());

            totalSalary += hours * logItem.getJmhourlyRate(); // 시급 * 근무 시간 계산
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
