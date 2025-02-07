package org.gooinpro.gooinproparttimerapi.review.domain;

import jakarta.persistence.*;
import lombok.*;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_review")
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eno")
    private EmployerEntity eno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private PartTimerEntity pno;

    @Column(nullable = false)
    private int rstart;

    @Column(nullable = false, columnDefinition = "VARCHAR(500)")
    private String rcontent;

    @Column(nullable = false)
    private Timestamp rregdate;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean rdelete;

}
