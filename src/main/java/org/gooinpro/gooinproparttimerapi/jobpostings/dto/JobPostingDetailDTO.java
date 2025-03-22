package org.gooinpro.gooinproparttimerapi.jobpostings.dto;

import lombok.Data;

import java.sql.Time;

@Data
public class JobPostingDetailDTO {

    private Long jpno;

    private String jpname; // name

    private String wroadAddress; // 도로명 주소

    private String wdetailAddress; // 상세 도로명 주소

    private int jphourlyRate;   //시급

    private String jpworkDays; // 근무요일

    private Time jpworkStartTime;   //근무 시작 시간

    private Time jpworkEndTime; //근무 종료 시간

    private Long eno; // 고용주 ID 추가

}
