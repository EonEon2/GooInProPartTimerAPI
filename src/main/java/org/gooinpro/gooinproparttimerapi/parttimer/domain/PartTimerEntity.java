package org.gooinpro.gooinproparttimerapi.parttimer.domain;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_partTimer")
public class PartTimerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long pno; // pk

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String pemail; // email

    @Column(nullable = false, columnDefinition = "VARCHAR(100)")
    private String ppw; // password

    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String pname; // 이름

    @Column(nullable = false)
    private Timestamp pbirth; // 생년월일

    @Column(nullable = false)
    private boolean pgender; // 성별 true = 남 , false = 여

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean pdelete; // 삭제여부

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp pregdate; // 등록 시간

    @Column(columnDefinition = "VARCHAR(100)")
    private String proadAddress; // 도로명 주소

    @Column(columnDefinition = "VARCHAR(100)")
    private String pdetailAddress; // 상세 주소
}
