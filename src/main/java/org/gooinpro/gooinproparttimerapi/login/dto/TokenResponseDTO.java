package org.gooinpro.gooinproparttimerapi.login.dto;

import lombok.Data;

@Data
public class TokenResponseDTO {

    private Long pno;

    private String pemail;

    private String pname;

    private String accessToken;

    private String refreshToken;

    private boolean isNew;

}
