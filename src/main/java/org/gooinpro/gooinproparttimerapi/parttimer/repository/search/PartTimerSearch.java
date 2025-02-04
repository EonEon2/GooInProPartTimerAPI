package org.gooinpro.gooinproparttimerapi.parttimer.repository.search;

import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerWorkHistoryDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerWorkPlaceDTO;
import java.util.List;

public interface PartTimerSearch {
    // 근무 이력 리스트
    List<PartTimerWorkHistoryDTO> getWorkHistory(Long pno);

    // 이전 근무지 목록
    List<PartTimerWorkPlaceDTO> getPreviousWorkplaces(Long pno);
}