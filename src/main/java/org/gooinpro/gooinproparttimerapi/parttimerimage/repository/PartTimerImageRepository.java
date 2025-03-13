package org.gooinpro.gooinproparttimerapi.parttimerimage.repository;

import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimerimage.domain.PartTimerImageEntity;
import org.gooinpro.gooinproparttimerapi.parttimerimage.repository.search.PartTimerImageSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartTimerImageRepository extends JpaRepository<PartTimerImageEntity, Long> {

    Optional<PartTimerImageEntity> findTopByPartTimerOrderByPinoDesc(PartTimerEntity partTimer);
}