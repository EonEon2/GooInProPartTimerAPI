package org.gooinpro.gooinproparttimerapi.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.login.dto.PartTimerDTO;
import org.gooinpro.gooinproparttimerapi.login.dto.PartTimerRegisterDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final PartTimerRepository partTimerRepository;

    //새로운 PartTimer 등록
    public PartTimerDTO partTimerRegisterService(PartTimerRegisterDTO partTimerRegisterDTO) {

        String pw = UUID.randomUUID().toString();

        PartTimerEntity partTimer = PartTimerEntity.builder()
                .pemail(partTimerRegisterDTO.getPemail())
                .ppw(pw)
                .pname(partTimerRegisterDTO.getPname())
                .pbirth(partTimerRegisterDTO.getPbirth())
                .pgender(partTimerRegisterDTO.isPgender())
                .proadAddress(partTimerRegisterDTO.getProadAddress())
                .pdetailAddress(partTimerRegisterDTO.getPdetailAddress())
                .build();

        PartTimerEntity saved = partTimerRepository.save(partTimer);

        return PartTimerDTO.builder()
                .pno(saved.getPno())
                .pemail(saved.getPemail())
                .pname(saved.getPname())
                .newUser(false)
                .build();
    }

//    public PartTimerDTO authenticate(String email, String password) {
//
//        Optional<PartTimerEntity> result = partTimerRepository.findByPemail(email);
//
//        PartTimerEntity partTimer = result.orElseThrow(() -> PartTimerExceptions.BAD_AUTH.get());
//
//        boolean match = passwordEncoder.matches(password, partTimer.getPpw());
//
//        if(!match) {
//            throw CommonExceptions.READ_ERROR.get();
//        }
//
//        PartTimerDTO partTimerDTO = new PartTimerDTO();
//        partTimerDTO.setPemail(email);
//        partTimerDTO.setPpw(partTimerDTO.getPpw());
//
//        return partTimerDTO;
//    }

    public PartTimerDTO findPartTimerService(String pemail) {

        log.info("find--------------------");

        Optional<PartTimerEntity> result = partTimerRepository.findByPemail(pemail);

        if(result.isPresent()) {

            return PartTimerDTO.builder()
                    .pno(result.get().getPno())
                    .pemail(result.get().getPemail())
                    .pname(result.get().getPname())
                    .newUser(false)
                    .build();
        }

        return PartTimerDTO.builder()
                .newUser(true)
                .build();
    }
}
