package org.gooinpro.gooinproparttimerapi.parttimer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.common.exception.CommonExceptions;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerRegiserDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.exception.PartTimerExceptions;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class PartTimerService {

    private final PartTimerRepository partTimerRepository;

    private final PasswordEncoder passwordEncoder;

    public void registerPartTimer(Long pno, PartTimerRegiserDTO partTimerRegiserDTO) {

        PartTimerEntity result = partTimerRepository.findById(pno).orElseThrow();

        result.setPname(partTimerRegiserDTO.getPname());
        result.setPbirth(partTimerRegiserDTO.getPbirth());
        result.setPgender(partTimerRegiserDTO.isPgender());
        result.setProadAddress(partTimerRegiserDTO.getProadAddress());
        result.setPdetailAddress(partTimerRegiserDTO.getPdetailAddress());

        partTimerRepository.save(result);

    }

        public PartTimerDTO authenticate(String email, String password) {

        Optional<PartTimerEntity> result = partTimerRepository.findByPemail(email);

            PartTimerEntity partTimer = result.orElseThrow(() -> PartTimerExceptions.BAD_AUTH.get());

        boolean match = passwordEncoder.matches(password, partTimer.getPpw());

        if(!match) {
            throw CommonExceptions.READ_ERROR.get();
        }

        PartTimerDTO partTimerDTO = new PartTimerDTO();
            partTimerDTO.setPemail(email);
            partTimerDTO.setPpw(partTimer.getPpw());

        return partTimerDTO;
    }


    private PartTimerDTO returnPartTimer(String email) {

        Optional<PartTimerEntity> result = partTimerRepository.findByPemail(email);

        PartTimerEntity partTimerEntity = new PartTimerEntity();
        PartTimerDTO partTimerDTO = new PartTimerDTO();

        if(result.isPresent()) {

            partTimerEntity = result.get();
            partTimerDTO.setPno(partTimerEntity.getPno());
            partTimerDTO.setPemail(partTimerEntity.getPemail());
            partTimerDTO.setPpw(partTimerEntity.getPpw());
            partTimerDTO.setPname(partTimerEntity.getPname());
            partTimerDTO.setPbirth(partTimerEntity.getPbirth());
            partTimerDTO.setPgender(partTimerEntity.isPgender());
            partTimerDTO.setPdetailAddress(partTimerEntity.getPdetailAddress());
            partTimerDTO.setProadAddress(partTimerEntity.getProadAddress());
            partTimerDTO.setPregdate(partTimerEntity.getPregdate());
            partTimerDTO.setPdelete(partTimerEntity.isPdelete());
            partTimerDTO.setNew(false);


            return partTimerDTO;
        }

        String pw = UUID.randomUUID().toString();
        PartTimerEntity newPartTimer = PartTimerEntity.builder()
                .pemail(email)
                .ppw(pw)
                .build();
        partTimerRepository.save(newPartTimer);

        Optional<PartTimerEntity> newResult = partTimerRepository.findByPemail(email);

        log.info("-----------------------");
        log.info(newResult);

        partTimerDTO.setPno(newResult.orElseThrow().getPno());
        partTimerDTO.setPemail(email);
        partTimerDTO.setPpw(pw);
        partTimerDTO.setNew(true);

        return partTimerDTO;

    }


    public PartTimerDTO authKakao(String accessToken) {

        String email = getEmailFromKakaoAccessToken(accessToken);

        log.info("email: " + email);

        return returnPartTimer(email);

    }

    private String getEmailFromKakaoAccessToken(String accessToken) {

        String kakaoGetUserURL = "https://kapi.kakao.com/v2/user/me";

        if(accessToken == null) {
            throw new RuntimeException("Access token is null");
        }
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(kakaoGetUserURL).build();
        ResponseEntity<LinkedHashMap> response =
                restTemplate.exchange(uriBuilder.toString(), HttpMethod.GET, entity, LinkedHashMap.class);

        log.info("---------------------------Kakao response : " + response);

        LinkedHashMap<String, LinkedHashMap> bodyMap = response.getBody();

        log.info("===============================");
        log.info(bodyMap);

        LinkedHashMap<String, String> kakaoAccount = bodyMap.get("kakao_account");

        log.info("kakaoAccount: " + kakaoAccount);

        return kakaoAccount.get("email");
    }

}
