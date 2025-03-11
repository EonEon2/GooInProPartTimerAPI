package org.gooinpro.gooinproparttimerapi.parttimerimage.repository;

import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimerimage.domain.PartTimerImageEntity;
import org.gooinpro.gooinproparttimerapi.parttimerimage.repository.search.PartTimerImageSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartTimerImageRepository extends JpaRepository<PartTimerImageEntity, Long>, PartTimerImageSearch {
}
