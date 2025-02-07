package org.gooinpro.gooinproparttimerapi.worklogs.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.gooinpro.gooinproparttimerapi.worklogs.domain.QWorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.domain.WorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.dto.WorkLogsDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;

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
                        workLogs.wlregdate,
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
}
