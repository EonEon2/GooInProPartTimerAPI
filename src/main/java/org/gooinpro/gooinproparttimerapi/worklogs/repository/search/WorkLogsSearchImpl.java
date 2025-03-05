package org.gooinpro.gooinproparttimerapi.worklogs.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.QJobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.repository.JobMatchingsRepository;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.gooinpro.gooinproparttimerapi.worklogs.domain.QWorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.domain.WorkLogsEntity;
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
import java.util.Optional;

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
                        workLogs.wlchangedStartTime,
                        workLogs.wlchangedEndTime,
                        workLogs.wldelete,
                        workLogs.eno.eno,
                        workLogs.pno.pno,
                        workLogs.jobMatching.jpno.jpname,
                        workLogs.jobMatching.jmhourlyRate))
                .where(workLogs.pno.pno.eq(pno)
                        .and(workLogs.wldelete.eq(false)));

        if (year != null) {
            query.where(workLogs.wlstartTime.year().eq(year));
        }
        if (month != null) {
            query.where(workLogs.wlstartTime.month().eq(month));
        }

        return query.fetch();
    }

    @Override
    public List<WorkLogsDTO> getWorkLogsByJob(Long pno, Long jmno) {
        QWorkLogsEntity workLogs = QWorkLogsEntity.workLogsEntity;

        return from(workLogs)
                .select(Projections.constructor(WorkLogsDTO.class,
                        workLogs.wlno,
                        workLogs.jobMatching.jmno,
                        workLogs.wlstartTime,
                        workLogs.wlendTime,
                        workLogs.wlworkStatus,
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
