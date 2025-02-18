package org.gooinpro.gooinproparttimerapi.jobpostings.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class JobPostingsListDTO {

    private Long jpno;

    private Long wpno;

    private String jpname; // name

    private String wroadAddress; // 도로명 주소

    private String wdetailAddress;

    private int jphourlyRate;   //시급

    private Timestamp jpenddate; // 마감 시간

    private String wlati; // 위도

    private String wlong; // 경도


}
