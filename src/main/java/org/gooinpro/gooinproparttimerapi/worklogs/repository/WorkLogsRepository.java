package org.gooinpro.gooinproparttimerapi.worklogs.repository;

import org.gooinpro.gooinproparttimerapi.worklogs.domain.WorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.repository.search.WorkLogsSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface WorkLogsRepository extends JpaRepository<WorkLogsEntity, Long>, WorkLogsSearch {

    @Query("SELECT wl FROM WorkLogsEntity wl WHERE wl.pno = :pno AND DATE(wl.wlstartTime) = CURRENT_DATE")
    Optional<WorkLogsEntity> findTodayWorkLogByPno(PartTimerEntity pno);

    @Query("SELECT wl.wlstartTime FROM WorkLogsEntity wl WHERE wl.pno.pno = :pno AND wl.jobMatching.jmno = :jmno AND DATE(wl.wlstartTime) = CURRENT_DATE")
    Optional<Timestamp> findTodayStartByPnoAndJmno(@Param("pno") Long pno, @Param("jmno") Long jmno);

    @Query("SELECT wl.wlendTime FROM WorkLogsEntity wl WHERE wl.pno.pno = :pno AND wl.jobMatching.jmno = :jmno AND DATE(wl.wlendTime) = CURRENT_DATE")
    Optional<Timestamp> findTodayEndByPnoAndJmno(@Param("pno") Long pno, @Param("jmno") Long jmno);
}
