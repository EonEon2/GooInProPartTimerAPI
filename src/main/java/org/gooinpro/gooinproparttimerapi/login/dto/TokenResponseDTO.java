package org.gooinpro.gooinproparttimerapi.login.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponseDTO {

    private Long pno;

    private String pemail;

    private String pname;

    private String accessToken;

    private String refreshToken;

    private boolean newUser;

}
