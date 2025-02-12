package org.gooinpro.gooinproparttimerapi.jobpostings.domain;

import jakarta.persistence.*;
import lombok.*;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.workplace.domain.WorkPlaceEntity;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_jobPostings")
public class JobPostingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jpno;

    @JoinColumn(name = "eno")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmployerEntity employer;

    @JoinColumn(name = "wpno")
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkPlaceEntity workPlace;

    private String jpname; // name

    private String jpcontent; // 내용

    @Column(columnDefinition = "timestamp default now()", nullable = false)
    private Timestamp jpregdate;    //공고 시작 시간

    private Timestamp jpenddate; // 공고 종료 시간 (null이면 고용인이 닫을때까지)

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean jpdelete = false;   //deflag(true = deleted)

    @Column(columnDefinition = "int default 0", nullable = false)
    private int jpvacancies = 0;    //모집 인원

    @Column(nullable = false)
    private int jphourlyRate;   //시급

    @Column(columnDefinition = "varchar(7) default '0000000'", nullable = false)
    private String jpworkDays = "0000000";  //근무 요일(월~일, 1이면 출근)

    @Column(nullable = false)
    private Integer jpminDuration;  //최소 근무 기간

    @Column(columnDefinition = "int default 0", nullable = true)
    private Integer jpmaxDuration = 0;

    private Time jpworkStartTime;   //근무 시작 시간

    private Time jpworkEndTime; //근무 종료 시간


}
