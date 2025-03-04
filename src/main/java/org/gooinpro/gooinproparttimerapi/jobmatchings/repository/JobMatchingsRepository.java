package org.gooinpro.gooinproparttimerapi.jobmatchings.repository;

import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search.JobMatchingsSearch;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface JobMatchingsRepository extends JpaRepository<JobMatchingsEntity, Long>, JobMatchingsSearch {

    @Query("SELECT j.jmworkStartTime FROM JobMatchingsEntity j Where j.pno.pno = :pno and j.jpno.jpno = :jpno")
    Timestamp findWorkStartTime(@Param("pno") Long pno, @Param("jpno") Long jpno);

}
