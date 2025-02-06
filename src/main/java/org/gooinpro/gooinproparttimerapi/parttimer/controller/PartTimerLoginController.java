package org.gooinpro.gooinproparttimerapi.parttimer.controller;


import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.TokenRequestDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.TokenResponseDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.exception.PartTimerExceptions;
import org.gooinpro.gooinproparttimerapi.parttimer.service.PartTimerService;
import org.gooinpro.gooinproparttimerapi.security.util.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/part/api/v1/login")
@Log4j2
@RequiredArgsConstructor
public class PartTimerLoginController {

    private final PartTimerService partTimerService;
    private final JWTUtil jwtUtil;

    @Value("${org.gooinpro.accessTime}")
    private int accessTime;

    @Value("${org.gooinpro.refreshTime}")
    private int refreshTime;

    @Value("${org.gooinpro.alwaysNew}")
    private boolean alwaysNew;


    private TokenResponseDTO generateTokenResponseDTO(PartTimerDTO partTimerDTO) {

        Map<String, Object> claimMap = Map.of("email", partTimerDTO.getPemail());

        String accessTokenStr = jwtUtil.createToken(claimMap, accessTime);
        String refreshTokenStr = jwtUtil.createToken(claimMap, refreshTime);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setPno(partTimerDTO.getPno());
        tokenResponseDTO.setPemail(partTimerDTO.getPemail());
        tokenResponseDTO.setPname(partTimerDTO.getPname());
        tokenResponseDTO.setAccessToken(accessTokenStr);
        tokenResponseDTO.setRefreshToken(refreshTokenStr);
        tokenResponseDTO.setNew(partTimerDTO.isNew());

        return tokenResponseDTO;

    }

    @PostMapping("makeToken")
    public ResponseEntity<TokenResponseDTO> makeToken(
            @RequestBody @Validated TokenRequestDTO tokenRequestDTO) {

        log.info("============================");
        log.info("makeToken");

        PartTimerDTO partTimerDTO =
                partTimerService.authenticate(tokenRequestDTO.getEmail(), tokenRequestDTO.getPw());

        log.info("partTimerDTO: " + partTimerDTO);

        Map<String, Object> claimMap =
                Map.of("email", partTimerDTO.getPemail());

        String accessToken = jwtUtil.createToken(claimMap, accessTime);
        String refreshToken = jwtUtil.createToken(claimMap, refreshTime);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setPemail(partTimerDTO.getPemail());
        tokenResponseDTO.setNew(partTimerDTO.isNew());

        return ResponseEntity.ok(tokenResponseDTO);
    }

    // 리프레쉬 토큰
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

        try{

            Map<String, Object> payload = jwtUtil.validateToken(accessTokenStr);

            String email = payload.get("email").toString();

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
            tokenResponseDTO.setPemail(email);
            tokenResponseDTO.setAccessToken(accessTokenStr);
            tokenResponseDTO.setRefreshToken(refreshToken);
            tokenResponseDTO.setNew(false);

            return ResponseEntity.ok(tokenResponseDTO);

        } catch (ExpiredJwtException ex) {

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

                TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
                tokenResponseDTO.setAccessToken(newAccessToken);
                tokenResponseDTO.setRefreshToken(newRefreshToken);
                tokenResponseDTO.setPemail(email);
                tokenResponseDTO.setNew(false);

                return ResponseEntity.ok(tokenResponseDTO);
            } catch (ExpiredJwtException ex2) {

                throw PartTimerExceptions.REQUIRE_SIGN_IN.get();
            }
        }
    }

    @PostMapping("deleteToken")
    public ResponseEntity<TokenResponseDTO> deleteToken(
            @RequestParam String partTimerEmail) {

        Map<String, Object> claimMap =
                Map.of("email", partTimerEmail);

        String accessToken = jwtUtil.createToken(claimMap, 0);
        String refreshToken = jwtUtil.createToken(claimMap, 0);

        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
        tokenResponseDTO.setAccessToken(accessToken);
        tokenResponseDTO.setRefreshToken(refreshToken);
        tokenResponseDTO.setPemail(partTimerEmail);

        return ResponseEntity.ok(tokenResponseDTO);
    }

    @RequestMapping("kakao")
    public ResponseEntity<TokenResponseDTO> kakaoToken(String accessToken) {

        PartTimerDTO partTimerDTO = partTimerService.authKakao(accessToken);

        log.info("kakao_userDTO: " + partTimerDTO);

        log.info("============______________=======" + generateTokenResponseDTO(partTimerDTO));

        return ResponseEntity.ok(generateTokenResponseDTO(partTimerDTO));
    }

}
