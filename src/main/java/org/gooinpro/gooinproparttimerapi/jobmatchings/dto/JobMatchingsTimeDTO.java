package org.gooinpro.gooinproparttimerapi.jobmatchings.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class JobMatchingsTimeDTO {

    private Long pno;

    private Long jpno;

    private Timestamp jmworkStartTime;

    private Timestamp jmworkEndTime;

}
