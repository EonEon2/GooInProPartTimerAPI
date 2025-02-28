package org.gooinpro.gooinproparttimerapi.worklogs.repository;

import org.gooinpro.gooinproparttimerapi.worklogs.domain.WorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.repository.search.WorkLogsSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;

import java.util.Optional;

public interface WorkLogsRepository extends JpaRepository<WorkLogsEntity, Long>, WorkLogsSearch {

    @Query("SELECT wl FROM WorkLogsEntity wl WHERE wl.pno = :pno AND DATE(wl.wlstartTime) = CURRENT_DATE")
    Optional<WorkLogsEntity> findTodayWorkLogByPno(PartTimerEntity pno);
}
