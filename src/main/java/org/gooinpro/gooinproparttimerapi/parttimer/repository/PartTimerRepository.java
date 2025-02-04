package org.gooinpro.gooinproparttimerapi.parttimer.repository;

import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartTimerRepository extends JpaRepository<PartTimerEntity, Long> {
    // 기본키(pno)로 조회
    Optional<PartTimerEntity> findByPno(Long pno);

    // 이메일(pemail)로 조회
    Optional<PartTimerEntity> findByPemail(String pemail);

    // 삭제되지 않은 근로자만 조회
    Optional<PartTimerEntity> findByPnoAndPdeleteIsFalse(Long pno);
}
