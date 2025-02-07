package org.gooinpro.gooinproparttimerapi.jobmatchings.domain;

import jakarta.persistence.*;
import lombok.*;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_jobMatchings")
public class JobMatchingsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jmno;              // 매칭 번호(PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eno")
    private EmployerEntity eno;     // 고용인 번호(FK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private PartTimerEntity pno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jpno")
    private JobPostingsEntity jpno;  // 구인공고 번호(FK)

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp jmregdate;    // 매칭 등록일

    @Column(nullable = false)
    private Timestamp jmstartDate;  // 근무 시작일e

    @Column
    private Timestamp jmendDate;    // 근무 종료일

    @Column(nullable = false)
    private Integer jmhourlyRate;   // 시급

    @Column(nullable = false, columnDefinition = "VARCHAR(7) DEFAULT '0000000'")
    private String jmworkDays;      // 근무요일 (월~일, 1이면 출근)

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean jmdelete;       // 삭제여부

    @Column
    private Timestamp jmworkStartTime;  // 근무 시작시간

    @Column
    private Timestamp jmworkEndTime;    // 근무 종료시간
}
