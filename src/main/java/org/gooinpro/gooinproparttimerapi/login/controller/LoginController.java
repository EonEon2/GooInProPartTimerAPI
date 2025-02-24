package org.gooinpro.gooinproparttimerapi.login.controller;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.login.dto.PartTimerDTO;
import org.gooinpro.gooinproparttimerapi.login.dto.PartTimerLoginDTO;
import org.gooinpro.gooinproparttimerapi.login.dto.PartTimerRegisterDTO;
import org.gooinpro.gooinproparttimerapi.login.dto.TokenResponseDTO;
import org.gooinpro.gooinproparttimerapi.login.exception.PartTimerExceptions;
import org.gooinpro.gooinproparttimerapi.login.service.LoginService;
import org.gooinpro.gooinproparttimerapi.security.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/part/api/v1/login")
public class LoginController {

    private final LoginService loginService;
    private final JWTUtil jwtUtil;

    @Value("${org.gooinpro.accessTime}")
    private int accessTime;

    @Value("${org.gooinpro.refreshTime}")
    private int refreshTime;

    @Value("${org.gooinpro.alwaysNew}")
    private boolean alwaysNew;

    private TokenResponseDTO generateTokenResponseDTO(PartTimerDTO partTimerDTO) {

        Map<String, Object> claimMap =
                Map.of("email", partTimerDTO.getPemail());

        String accessTokenStr = jwtUtil.createToken(claimMap, accessTime);
        String refreshTokenStr = jwtUtil.createToken(claimMap, refreshTime);

        return TokenResponseDTO.builder()
                .pno(partTimerDTO.getPno())
                .pname(partTimerDTO.getPname())
                .accessToken(accessTokenStr)
                .refreshToken(refreshTokenStr)
                .pemail(partTimerDTO.getPemail())
                .isNew(partTimerDTO.isNew())
                .build();
    }

//    @PostMapping("makeToken")
//    public ResponseEntity<TokenResponseDTO> makeToken(
//            @RequestBody @Validated TokenRequestDTO tokenRequestDTO) {
//
//        log.info("============================");
//        log.info("makeToken");
//
//        PartTimerDTO partTimerDTO =
//                loginService.authenticate(tokenRequestDTO.getEmail(), tokenRequestDTO.getPw());
//
//        Map<String, Object> claimMap =
//                Map.of("email", partTimerDTO.getPemail());
//
//        String accessToken = jwtUtil.createToken(claimMap, accessTime);
//        String refreshToken = jwtUtil.createToken(claimMap, refreshTime);
//
//        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
//        tokenResponseDTO.setAccessToken(accessToken);
//        tokenResponseDTO.setRefreshToken(refreshToken);
//        tokenResponseDTO.setPemail(partTimerDTO.getPemail());
//        tokenResponseDTO.setNew(partTimerDTO.isNew());
//
//        return ResponseEntity.ok(tokenResponseDTO);
//    }

    @PostMapping(value = "refreshToken",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponseDTO> refreshToken(
            @RequestHeader("Authorization") String accessToken, String refreshToken) {

        if(accessToken == null || refreshToken == null) {
            throw PartTimerExceptions.TOKEN_NOT_ENOUGH.get();
        }

        if(!accessToken.startsWith("Bearer ")) {
            throw PartTimerExceptions.ACCESSTOKEN_TOO_SHORT.get();
        }

        String accessTokenStr = accessToken.substring("Bearer ".length());

        try {

            Map<String, Object> payload = jwtUtil.validateToken(accessTokenStr);

            String email = payload.get("email").toString();

            TokenResponseDTO tokenResponseDTO = TokenResponseDTO.builder()
                    .accessToken(accessTokenStr)
                    .refreshToken(refreshToken)
                    .pemail(email)
                    .isNew(false)
                    .build();

            return ResponseEntity.ok(tokenResponseDTO);
        } catch(ExpiredJwtException ex) {

            try {

                Map<String, Object> payload = jwtUtil.validateToken(refreshToken);

                String email = payload.get("email").toString();
                String newAccessToken = null;
                String newRefreshToken = null;

                if(alwaysNew) {

                    Map<String, Object> claimMap = Map.of("email", email);
                    newAccessToken = jwtUtil.createToken(claimMap, accessTime);
                    newRefreshToken = jwtUtil.createToken(claimMap, refreshTime);
                }

                TokenResponseDTO tokenResponseDTO = TokenResponseDTO.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .pemail(email)
                        .isNew(false)
                        .build();

                return ResponseEntity.ok(tokenResponseDTO);
            } catch (ExpiredJwtException ex2) {

                throw PartTimerExceptions.REQUIRE_SIGN_IN.get();
            }
        }
    }

    @GetMapping("find/{pemail}") // pathvariable
    public ResponseEntity<TokenResponseDTO> find(@PathVariable String pemail) {

        PartTimerDTO partTimerDTO = loginService.findPartTimerService(pemail);

        if(partTimerDTO.isNew())
            return ResponseEntity.ok(TokenResponseDTO.builder()
                            .isNew(true).build());

        return ResponseEntity.ok(generateTokenResponseDTO(partTimerDTO));
    }

    @PostMapping("register")
    public ResponseEntity<TokenResponseDTO> register(@RequestBody PartTimerRegisterDTO partTimerRegisterDTO) {

        return ResponseEntity.ok(
                generateTokenResponseDTO(loginService.partTimerRegisterService(partTimerRegisterDTO)));
    }
}
