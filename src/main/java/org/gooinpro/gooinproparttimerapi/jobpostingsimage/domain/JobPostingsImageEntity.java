package org.gooinpro.gooinproparttimerapi.jobpostingsimage.domain;


import jakarta.persistence.*;
import lombok.*;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tbl_jobPostingImage")
public class JobPostingsImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jpino;

    @JoinColumn(name = "jpno")
    @ManyToOne(fetch = FetchType.LAZY)
    private JobPostingsEntity jpno;

    private String jpifilename;
}
