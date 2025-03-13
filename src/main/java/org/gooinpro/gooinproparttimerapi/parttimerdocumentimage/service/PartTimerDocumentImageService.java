package org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.domain.PartTimerDocumentImageEntity;
import org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.dto.PartTimerDocumentImageRegisterDTO;
import org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.repository.PartTimerDocumentImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class PartTimerDocumentImageService {

    private final PartTimerDocumentImageRepository partTimerDocumentImageRepository;
    private final PartTimerRepository partTimerRepository;

    public String partTimerDocumentImageRegister(PartTimerDocumentImageRegisterDTO partTimerDocumentImageRegisterDTO) {
        Optional<PartTimerEntity> pno = partTimerRepository.findByPno(partTimerDocumentImageRegisterDTO.getPno());

        List<String> documentImageList = partTimerDocumentImageRegisterDTO.getPdifilename();

        for (String documentImage : documentImageList) {

            PartTimerDocumentImageEntity partTimerDocumentImageEntity = PartTimerDocumentImageEntity.builder()
                    .pdifilename(documentImage)
                    .partTimer(pno.get())
                    .build();

            partTimerDocumentImageRepository.save(partTimerDocumentImageEntity);
        }


        return "success save";
    }
}
