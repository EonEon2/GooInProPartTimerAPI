package org.gooinpro.gooinproparttimerapi.login.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TokenRequestDTO {

    @NotNull
    private String email;

    @NotNull
    private String pw;
}
