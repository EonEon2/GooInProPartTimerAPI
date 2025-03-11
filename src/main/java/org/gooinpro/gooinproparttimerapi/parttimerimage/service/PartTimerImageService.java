package org.gooinpro.gooinproparttimerapi.parttimerimage.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.gooinpro.gooinproparttimerapi.parttimerimage.domain.PartTimerImageEntity;
import org.gooinpro.gooinproparttimerapi.parttimerimage.dto.PartTimerImageRegisterDTO;
import org.gooinpro.gooinproparttimerapi.parttimerimage.repository.PartTimerImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class PartTimerImageService {

    private final PartTimerImageRepository partTimerImageRepository;
    private final PartTimerRepository partTimerRepository;


    public String partTimerImageRegister(PartTimerImageRegisterDTO partTimerImageRegisterDTO) {

        Optional<PartTimerEntity> pno = partTimerRepository.findByPno(partTimerImageRegisterDTO.getPno());

        List<String> ImageList = partTimerImageRegisterDTO.getPifilename();

        log.info(ImageList);

        for (String Image: ImageList) {

            PartTimerImageEntity partTimerImage = PartTimerImageEntity.builder()
                    .pifilename(Image)
                    .partTimer(pno.get())
                    .build();

            partTimerImageRepository.save(partTimerImage);
        }



        return "success register";
    }
}