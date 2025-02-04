package org.gooinpro.gooinproparttimerapi.parttimer.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PartTimerDTO {

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
