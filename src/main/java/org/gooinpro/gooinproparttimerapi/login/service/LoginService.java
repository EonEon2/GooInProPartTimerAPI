package org.gooinpro.gooinproparttimerapi.login.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.common.exception.CommonExceptions;
import org.gooinpro.gooinproparttimerapi.login.dto.PartTimerDTO;
import org.gooinpro.gooinproparttimerapi.login.exception.PartTimerExceptions;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final PartTimerRepository partTimerRepository;
    private final PasswordEncoder passwordEncoder;

    public PartTimerDTO authenticate(String email, String password) {

        Optional<PartTimerEntity> result = partTimerRepository.findByPemail(email);

        PartTimerEntity partTimer = result.orElseThrow(() -> PartTimerExceptions.BAD_AUTH.get());

        boolean match = passwordEncoder.matches(password, partTimer.getPpw());

        if(!match) {
            throw CommonExceptions.READ_ERROR.get();
        }

        PartTimerDTO partTimerDTO = new PartTimerDTO();
        partTimerDTO.setPemail(email);
        partTimerDTO.setPpw(partTimerDTO.getPpw());

        return partTimerDTO;
    }
}
