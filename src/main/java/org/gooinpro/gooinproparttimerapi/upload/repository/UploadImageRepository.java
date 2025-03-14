package org.gooinpro.gooinproparttimerapi.upload.repository;

import org.gooinpro.gooinproparttimerapi.upload.domain.UploadImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadImageRepository extends JpaRepository<UploadImageEntity, Long> {

    // 특정 파트타이머의 이미지 찾기
    Optional<UploadImageEntity> findByPartTimerId(Long partTimerId);

    // 특정 파일명으로 이미지 찾기
    Optional<UploadImageEntity> findByFilename(String filename);

    // 특정 파트타이머의 이미지 삭제
    void deleteByPartTimerId(Long partTimerId);
}
