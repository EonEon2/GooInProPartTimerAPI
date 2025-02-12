package org.gooinpro.gooinproparttimerapi.jobmatchings.repository;

import org.gooinpro.gooinproparttimerapi.jobmatchings.domain.JobMatchingsEntity;
import org.gooinpro.gooinproparttimerapi.jobmatchings.repository.search.JobMatchingsSearch;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobMatchingsRepository extends JpaRepository<JobMatchingsEntity, Long>, JobMatchingsSearch {

}
