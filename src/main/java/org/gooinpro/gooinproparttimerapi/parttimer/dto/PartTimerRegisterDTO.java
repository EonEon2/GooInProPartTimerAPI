package org.gooinpro.gooinproparttimerapi.parttimer.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PartTimerRegisterDTO {

    private String pname;

    private String proadAddress;

    private String pdetailAddress;

    private boolean pgender;

    private Timestamp pbirth;

}
