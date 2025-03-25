package org.gooinpro.gooinproparttimerapi.jobpostingsapplication.domain;

import jakarta.persistence.*;
import lombok.*;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.workplace.domain.WorkPlaceEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tbl_jobPostingApplication")
public class JobPostingsApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jpano;

    private String jpacontent;

    private int jpahourlyRate;

    private boolean jpadelete;

    @Column(nullable = false)
    private int jpastatus;  //0: 대기(고용인 확인 전), 1: 수락, 2: 거절

    @JoinColumn(name = "pno")
    @ManyToOne(fetch = FetchType.LAZY)
    private PartTimerEntity partTimer;

    @JoinColumn(name = "jpno")
    @ManyToOne(fetch = FetchType.LAZY)
    private JobPostingsEntity jobPostings;
}
