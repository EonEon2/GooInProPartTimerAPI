package org.gooinpro.gooinproparttimerapi.jobpostings.repository;

import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.repository.search.JobPostingsSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobPostingsRepository extends JpaRepository<JobPostingsEntity, Long>, JobPostingsSearch {

    Optional<JobPostingsEntity> findByJpno(Long jpno);

}
