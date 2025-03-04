package org.gooinpro.gooinproparttimerapi.login.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartTimerDTO {

    private Long pno; // pk

    private String pemail; // email

    private String pname; // 이름

    private boolean newUser;  //새로운 사용자 Flag
}
