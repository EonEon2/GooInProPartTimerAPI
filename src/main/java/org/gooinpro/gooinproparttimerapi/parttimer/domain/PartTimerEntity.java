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
    private Long pno;

    private Timestamp pbirth;

    private boolean pdelete;

    private String pemail;

    private boolean pgender;

    private String pname;

    private Timestamp pregdate;

    private String proadAddress;

    private String pdetailAddress;

}
