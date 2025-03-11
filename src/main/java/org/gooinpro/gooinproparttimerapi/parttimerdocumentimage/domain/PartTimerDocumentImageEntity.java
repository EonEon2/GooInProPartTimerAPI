package org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.domain;


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
@Table(name = "tbl_partTimerDocumentImage")
public class PartTimerDocumentImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pdino;

    private String pdifilename;

    @JoinColumn(name = "pno")
    @ManyToOne(fetch = FetchType.LAZY)
    private PartTimerEntity partTimer;
}
