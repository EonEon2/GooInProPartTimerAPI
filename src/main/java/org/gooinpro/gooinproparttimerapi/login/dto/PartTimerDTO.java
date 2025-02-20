package org.gooinpro.gooinproparttimerapi.login.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PartTimerDTO {

    private Long pno; // pk

    private String pemail; // email

    private String ppw; //pw

    private String pname; // 이름

    private Timestamp pbirth; // 생년월일

    private boolean pgender; // 성별 true = 남 , false = 여

    private boolean pdelete; // 삭제여부

    private String proadAddress; // 도로명 주소

    private String pdetailAddress; // 상세 주소

    private boolean isNew;  //새로운 사용자 Flag
}
