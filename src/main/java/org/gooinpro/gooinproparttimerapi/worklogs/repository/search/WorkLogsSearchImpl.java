package org.gooinpro.gooinproparttimerapi.worklogs.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.worklogs.domain.QWorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.domain.WorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.WorkLogsDTO;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.*;
import org.gooinpro.gooinproparttimerapi.worklogs.repository.WorkLogsRepository;
import org.gooinpro.gooinproparttimerapi.worklogs.service.WorkLogsService;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Log4j2
public class WorkLogsSearchImpl extends QuerydslRepositorySupport implements WorkLogsSearch {


    public WorkLogsSearchImpl() {
        super(WorkLogsEntity.class);
    }


    @Override
    public List<WorkLogsDTO> getMonthlyWorkLogs(Long pno, Integer year, Integer month) {
        QWorkLogsEntity workLogs = QWorkLogsEntity.workLogsEntity;

        JPQLQuery<WorkLogsDTO> query = from(workLogs)
                .select(Projections.constructor(WorkLogsDTO.class,
                        workLogs.wlno,
                        workLogs.jobMatching.jmno,
                        workLogs.wlstartTime,
                        workLogs.wlendTime,
                        workLogs.wlworkStatus,
                        workLogs.wlregdate,
                        workLogs.wlchangedStartTime,
                        workLogs.wlchangedEndTime,
                        workLogs.wldelete,
                        workLogs.eno.eno,
                        workLogs.pno.pno,
                        workLogs.jobMatching.jpno.jpname, // jpname 확인 대상
                        workLogs.jobMatching.jmhourlyRate)) // jmhourlyRate 확인 대상
                .where(workLogs.pno.pno.eq(pno)
                        .and(workLogs.wldelete.eq(false)));

        if (year != null) {
            query.where(workLogs.wlstartTime.year().eq(year));
        }
        if (month != null) {
            query.where(workLogs.wlstartTime.month().eq(month));
        }

        // 반환된 데이터 로그 출력
        List<WorkLogsDTO> result = query.fetch();
        log.info("Fetched WorkLogs for getMonthlyWorkLogs: {}", result);

        // WorkLogsDTO의 jpname과 jmhourlyRate 값 확인
        for (WorkLogsDTO logItem : result) { // log -> logItem으로 이름 변경
            log.info("jpname: {}, jmhourlyRate: {}", logItem.getJpname(), logItem.getJmhourlyRate());
        }

        return result;
    }

    @Override
    public List<WorkLogsDTO> getWorkLogsByJob(Long pno, Long jmno) {
        QWorkLogsEntity workLogs = QWorkLogsEntity.workLogsEntity;

        List<WorkLogsDTO> result = from(workLogs)
                .select(Projections.constructor(WorkLogsDTO.class,
                        workLogs.wlno,
                        workLogs.jobMatching.jmno,
                        workLogs.wlstartTime,
                        workLogs.wlendTime,
                        workLogs.wlworkStatus,
                        workLogs.wlregdate,         // wlregdate 추가
                        workLogs.wlchangedStartTime,
                        workLogs.wlchangedEndTime,
                        workLogs.wldelete,
                        workLogs.eno.eno,
                        workLogs.pno.pno,
                        workLogs.jobMatching.jpno.jpname,
                        workLogs.jobMatching.jmhourlyRate))
                .where(workLogs.pno.pno.eq(pno)
                        .and(workLogs.jobMatching.jmno.eq(jmno))
                        .and(workLogs.wldelete.eq(false)))
                .fetch();

        // 반환된 데이터 로그 출력
        log.info("Fetched WorkLogs for getWorkLogsByJob: {}", result);

        // WorkLogsDTO의 jpname과 jmhourlyRate 값 확인
        for (WorkLogsDTO logItem : result) {
            log.info("jpname: {}, jmhourlyRate: {}", logItem.getJpname(), logItem.getJmhourlyRate());
        }
        return result;
    }

    @Override
    public WorkLogsInDTO getTodayStartTimeStatus(Long pno, Long jmno) {
        QWorkLogsEntity workLogs = QWorkLogsEntity.workLogsEntity;

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        JPQLQuery<WorkLogsEntity> query = from(workLogs);
        query.where(workLogs.pno.pno.eq(pno));
        query.where(workLogs.jobMatching.jmno.eq(jmno));
        query.where(workLogs.wlstartTime.between(
                Timestamp.valueOf(startOfDay),
                Timestamp.valueOf(endOfDay)
        ));

        JPQLQuery<WorkLogsInDTO> tupleQuery = query.select(
                Projections.bean(WorkLogsInDTO.class,
                        workLogs.wlstartTime,
                        workLogs.wlworkStatus)
        );

        WorkLogsInDTO result = tupleQuery.fetchOne();

        return result;
    }

    @Override
    public WorkLogsOutDTO getTodayEndTimeStatus(Long pno, Long jmno) {
        QWorkLogsEntity workLogs = QWorkLogsEntity.workLogsEntity;

        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        JPQLQuery<WorkLogsEntity> query = from(workLogs);

        query.where(workLogs.pno.pno.eq(pno));
        query.where(workLogs.jobMatching.jmno.eq(jmno));
        query.where(workLogs.wlstartTime.between(
                Timestamp.valueOf(startOfDay),
                Timestamp.valueOf(endOfDay)
        ));

        JPQLQuery<WorkLogsOutDTO> tupleQuery = query.select(
                Projections.bean(WorkLogsOutDTO.class,
                        workLogs.wlendTime,
                        workLogs.wlworkStatus)
        );

        WorkLogsOutDTO result = tupleQuery.fetchOne();

        return result;
    }
}
