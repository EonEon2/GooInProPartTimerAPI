package org.gooinpro.gooinproparttimerapi.employer.repository;

import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.employer.repository.search.EmployerSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<EmployerEntity, Long>, EmployerSearch {
}
