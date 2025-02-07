package org.gooinpro.gooinproparttimerapi.workplace.repository.search;

import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.workplace.dto.WorkPlaceListDTO;

public interface WorkPlaceSearch {

    PageResponseDTO<WorkPlaceListDTO> WorkPlaceList(PageRequestDTO pageRequestDTO);
}
