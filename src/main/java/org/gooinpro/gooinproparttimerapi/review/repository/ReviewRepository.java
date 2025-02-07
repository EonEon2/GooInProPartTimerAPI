package org.gooinpro.gooinproparttimerapi.review.repository;

import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.review.domain.ReviewEntity;
import org.gooinpro.gooinproparttimerapi.review.repository.search.ReviewSearch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, ReviewSearch {

    List<ReviewEntity> findByPnoAndRdeleteIsFalse(PartTimerEntity pno);

}
