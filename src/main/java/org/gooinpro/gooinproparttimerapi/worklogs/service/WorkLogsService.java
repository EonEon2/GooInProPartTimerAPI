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

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class WorkLogsService {

    private final WorkLogsRepository workLogsRepository;
    private final PartTimerRepository partTimerRepository;
    private final JobMatchingsRepository jobMatchingsRepository;

    public WorkLogsInDTO startTime(WorkLogsTimeDTO workLogsTimeDTO) {

        PartTimerEntity partTimer = partTimerRepository.findById(workLogsTimeDTO.getPno()).orElseThrow();
        JobMatchingsEntity jobMatchings = jobMatchingsRepository.findById(workLogsTimeDTO.getJmno()).orElseThrow();

        Integer workStatus = 0;

        Timestamp timenow = new Timestamp(System.currentTimeMillis());
        Timestamp timerule = jobMatchings.getJmworkStartTime();

        if(timenow.after(timerule)) workStatus = 1;

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

        Integer workStatus = 0;  // 0:정상,1:지각,2:조퇴,3:결근

        Timestamp timenow = new Timestamp(System.currentTimeMillis());
        Timestamp timerule = jobMatchings.getJmworkEndTime();

        if(timenow.before(timerule)) workStatus = 2;

        WorkLogsEntity workLogsEntity = new WorkLogsEntity();
        workLogsEntity.setPno(partTimer);
        workLogsEntity.setJobMatching(jobMatchings);
        workLogsEntity.setWlendTime(timenow);
        workLogsEntity.setWlworkStatus(workStatus);

        workLogsRepository.save(workLogsEntity);

        return WorkLogsOutDTO.builder()
                .wlendTime(timenow)
                .wlworkStatus(workStatus)
                .build();


    }
}
