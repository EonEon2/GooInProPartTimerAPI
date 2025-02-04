package org.gooinpro.gooinproparttimerapi.jobpostings.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class JobPostingsListDTO {

    private Long jpno;

    private String jpname; // name

    private String wroadAddress; // 도로명 주소

    private int jphourlyRate;   //시급

    private Timestamp jpenddate;
}
