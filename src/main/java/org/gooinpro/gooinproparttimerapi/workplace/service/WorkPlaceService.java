package org.gooinpro.gooinproparttimerapi.workplace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.workplace.dto.WorkPlaceListDTO;
import org.gooinpro.gooinproparttimerapi.workplace.repository.WorkPlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class WorkPlaceService {

    private final WorkPlaceRepository workPlaceRepository;

    public PageResponseDTO<WorkPlaceListDTO> WorkPlaceList(PageRequestDTO pageRequestDTO) {

        return workPlaceRepository.WorkPlaceList(pageRequestDTO);
    }
}
