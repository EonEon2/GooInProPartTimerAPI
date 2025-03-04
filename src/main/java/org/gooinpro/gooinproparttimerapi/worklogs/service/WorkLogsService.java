package org.gooinpro.gooinproparttimerapi.worklogs.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.repository.JobMatchingsRepository;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.gooinpro.gooinproparttimerapi.worklogs.domain.WorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.WorkLogsInDTO;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.WorkLogsOutDTO;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.WorkLogsTimeDTO;
import org.gooinpro.gooinproparttimerapi.worklogs.repository.WorkLogsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class WorkLogsService {

    private final WorkLogsRepository workLogsRepository;
    private final PartTimerRepository partTimerRepository;
    private final JobMatchingsRepository jobMatchingsRepository;

    public WorkLogsInDTO startTime(WorkLogsTimeDTO workLogsTimeDTO) {

        log.info(workLogsTimeDTO.getPno());
        log.info(workLogsTimeDTO.getJmno());

        PartTimerEntity partTimer = partTimerRepository.findById(workLogsTimeDTO.getPno()).orElseThrow();
        JobMatchingsEntity jobMatchings = jobMatchingsRepository.findById(workLogsTimeDTO.getJmno()).orElseThrow();

        Integer workStatus = 0;

        Timestamp timenow = new Timestamp(System.currentTimeMillis());
        LocalTime changeTimenow = timenow.toLocalDateTime().toLocalTime();

        Timestamp timerule = jobMatchings.getJmworkStartTime();
        LocalTime changeTimeRule = timerule.toLocalDateTime().toLocalTime();

        log.info(changeTimenow);
        log.info(changeTimeRule);

        if(changeTimenow.isAfter(changeTimeRule)) workStatus = 1;

        WorkLogsEntity workLogsEntity = new WorkLogsEntity();
        workLogsEntity.setPno(partTimer);
        workLogsEntity.setJobMatching(jobMatchings);
        workLogsEntity.setWlstartTime(timenow);
        workLogsEntity.setWlworkStatus(workStatus);

        workLogsRepository.save(workLogsEntity);

        return WorkLogsInDTO.builder()
                .wlstartTime(timenow)
                .wlworkStatus(workStatus)
                .build();
    }

    public WorkLogsOutDTO endTime(WorkLogsTimeDTO workLogsTimeDTO) {

        PartTimerEntity partTimer = partTimerRepository.findById(workLogsTimeDTO.getPno()).orElseThrow();
        JobMatchingsEntity jobMatchings = jobMatchingsRepository.findById(workLogsTimeDTO.getJmno()).orElseThrow();
        WorkLogsEntity workLogs = workLogsRepository.findTodayWorkLogByPno(partTimer).orElseThrow();

        Integer workStatus = workLogs.getWlworkStatus();  // 0:정상,1:지각,2:조퇴,3:결근,4:지각조퇴

        log.info(workLogs.getWlno());
        log.info("----------------------------------");
        log.info(workStatus);

        Timestamp timenow = new Timestamp(System.currentTimeMillis());
        LocalTime changeTimenow = timenow.toLocalDateTime().toLocalTime();

        Timestamp timerule = jobMatchings.getJmworkEndTime();
        LocalTime changeTimeRule = timerule.toLocalDateTime().toLocalTime();

        if(changeTimenow.isBefore(changeTimeRule)) {
            if(workStatus.equals(1)) {
                workStatus = 4;
            } else{
                workStatus = 2;
            }
        }

        workLogs.setWlendTime(timenow);
        workLogs.setWlworkStatus(workStatus);

        workLogsRepository.save(workLogs);

        log.info(workLogs);

        return WorkLogsOutDTO.builder()
                .wlendTime(timenow)
                .wlworkStatus(workStatus)
                .build();
    }

    public Optional<Timestamp> getRealStartTime(Long pno, Long jmno) {
        return workLogsRepository.findTodayStartByPnoAndJmno(pno, jmno);
    }

    public Optional<Timestamp> getRealEndTime(Long pno, Long jmno) {
        return workLogsRepository.findTodayEndByPnoAndJmno(pno, jmno);
    }
}
