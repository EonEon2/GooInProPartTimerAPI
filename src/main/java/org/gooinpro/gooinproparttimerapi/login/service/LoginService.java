package org.gooinpro.gooinproparttimerapi.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.login.dto.PartTimerDTO;
import org.gooinpro.gooinproparttimerapi.login.dto.PartTimerLoginDTO;
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

    private PartTimerDTO partTimerRegisterService(PartTimerLoginDTO partTimerLoginDTO) {

        String pw = UUID.randomUUID().toString();

        PartTimerEntity partTimerEntity = PartTimerEntity.builder()
                .pemail(partTimerLoginDTO.getPemail())
                .pname(partTimerLoginDTO.getPname())
                .ppw(pw)
                .build();

        PartTimerEntity result = partTimerRepository.save(partTimerEntity);

        return PartTimerDTO.builder()
                .pno(result.getPno())
                .pemail(result.getPemail())
                .pname(result.getPname())
                .isNew(true)
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

    public PartTimerDTO findPartTimerService(PartTimerLoginDTO partTimerLoginDTO) {

        Optional<PartTimerEntity> result = partTimerRepository.findByPemail(partTimerLoginDTO.getPemail());

        if(result.isPresent()) {

            return PartTimerDTO.builder()
                    .pno(result.get().getPno())
                    .pemail(result.get().getPemail())
                    .pname(result.get().getPname())
                    .isNew(false)
                    .build();
        }

        return partTimerRegisterService(partTimerLoginDTO);
    }
}
