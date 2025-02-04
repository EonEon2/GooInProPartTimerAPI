package org.gooinpro.gooinproparttimerapi.parttimer.repository;

import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.search.PartTimerSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartTimerRepository extends JpaRepository<PartTimerEntity, Long>, PartTimerSearch {
}
