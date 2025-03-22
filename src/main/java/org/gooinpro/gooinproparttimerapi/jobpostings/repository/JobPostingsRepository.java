package org.gooinpro.gooinproparttimerapi.jobpostings.repository;

import org.gooinpro.gooinproparttimerapi.jobpostings.domain.JobPostingsEntity;
import org.gooinpro.gooinproparttimerapi.jobpostings.repository.search.JobPostingsSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobPostingsRepository extends JpaRepository<JobPostingsEntity, Long>, JobPostingsSearch {

    Optional<JobPostingsEntity> findByJpno(Long jpno);

    @Query("SELECT j.employer.eno FROM JobPostingsEntity j WHERE j.jpno = :jpno")
    Long getEmployerIdByJpno(@Param("jpno") Long jpno);

    @Query("SELECT j.jpname FROM JobPostingsEntity j WHERE j.employer.eno = :eno")
    List<String> findJobPostingNamesByEmployerId(@Param("eno") Long eno);
}
