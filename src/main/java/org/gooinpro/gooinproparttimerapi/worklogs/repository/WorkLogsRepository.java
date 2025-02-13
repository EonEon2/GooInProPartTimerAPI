package org.gooinpro.gooinproparttimerapi.worklogs.repository;

import org.gooinpro.gooinproparttimerapi.worklogs.domain.WorkLogsEntity;
import org.gooinpro.gooinproparttimerapi.worklogs.repository.search.WorkLogsSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogsRepository extends JpaRepository<WorkLogsEntity, Long>, WorkLogsSearch {
}
