package org.gooinpro.gooinproparttimerapi.parttimerimage.domain;


import jakarta.persistence.*;
import lombok.*;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tbl_partTimerImage")
public class PartTimerImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pino;

    private String pifilename;

    @JoinColumn(name = "pno")
    @ManyToOne(fetch = FetchType.LAZY)
    private PartTimerEntity partTimer;
}
