package org.gooinpro.gooinproparttimerapi.parttimerimage.domain;

import jakarta.persistence.*;
import lombok.*;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString
@Table(name = "tbl_partTimerImage")
public class PartTimerImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pino; // 파트타이머 이미지 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pno")
    private PartTimerEntity partTimer; // 파트타이머와의 관계

    private String pifilename; // 이미지 파일명
}