package org.gooinpro.gooinproparttimerapi.workplace.repository;

import org.gooinpro.gooinproparttimerapi.workplace.domain.WorkPlaceEntity;
import org.gooinpro.gooinproparttimerapi.workplace.repository.search.WorkPlaceSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkPlaceRepository extends JpaRepository<WorkPlaceEntity, Long>, WorkPlaceSearch {
}
