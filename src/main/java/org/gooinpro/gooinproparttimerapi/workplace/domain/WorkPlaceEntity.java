package org.gooinpro.gooinproparttimerapi.workplace.domain;

import jakarta.persistence.*;
import lombok.*;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_workPlace")
public class WorkPlaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wpno;

    @JoinColumn(name = "eno")
    @ManyToOne(fetch = FetchType.LAZY)
    private EmployerEntity employer;

    private String wroadAddress; // 도로명 주소

    private String wdetailAddress; // 상세 주소

    private String wlati; // 위도

    private String wlong; // 경도

    private boolean wdelete;

}
