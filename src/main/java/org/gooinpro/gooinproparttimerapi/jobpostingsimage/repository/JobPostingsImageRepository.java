package org.gooinpro.gooinproparttimerapi.jobpostingsimage.repository;

import org.gooinpro.gooinproparttimerapi.jobpostingsimage.domain.JobPostingsImageEntity;
import org.gooinpro.gooinproparttimerapi.jobpostingsimage.repository.search.JobPostingsImageSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingsImageRepository extends JpaRepository<JobPostingsImageEntity, Long>, JobPostingsImageSearch {
}
