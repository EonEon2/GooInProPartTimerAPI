package org.gooinpro.gooinproparttimerapi.worklogs.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "tbl_workLogs")
public class WorkLogsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wlno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jmno")
    private JobMatchingsEntity jobMatching;

    private Timestamp wlstartTime; // 출근시간

    private Timestamp wlendTime; // 퇴근시간

    private Integer wlworkStatus; // 0:정상,1:지각,2:조퇴,3:결근

    private Time wlchangedStartTime; // 변경 출근

    private Time wlchangedEndTime; // 변경 퇴근

    @Column(nullable = false)
    private boolean wldelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eno")
    private EmployerEntity eno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private PartTimerEntity pno;
}
