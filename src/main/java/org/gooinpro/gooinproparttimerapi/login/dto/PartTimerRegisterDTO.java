package org.gooinpro.gooinproparttimerapi.login.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PartTimerRegisterDTO {

    private String pemail; // email

    private String pname; // 이름

    private Timestamp pbirth; // 생년월일

    private boolean pgender; // 성별 true = 남 , false = 여

    //주소는 입력하지 않을 수 있음

    private String proadAddress; // 도로명 주소

    private String pdetailAddress; // 상세 주소
}
