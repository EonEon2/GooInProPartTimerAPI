package org.gooinpro.gooinproparttimerapi.upload.service;

import lombok.RequiredArgsConstructor;
import org.gooinpro.gooinproparttimerapi.upload.domain.UploadImageEntity;
import org.gooinpro.gooinproparttimerapi.upload.repository.UploadImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadImageService {

    private final UploadImageRepository uploadImageRepository;

    // 파일 저장 경로 (환경 변수나 설정 파일에서 가져올 수도 있음)
    private final String uploadDir = "./uploads";

    /**
     * 이미지 업로드 및 데이터베이스 저장
     */
    @Transactional
    public UploadImageEntity uploadImage(MultipartFile file, Long partTimerId) {
        try {
            // 1. 파일 저장
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath); // 디렉토리가 없으면 생성
            Path targetLocation = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation);

            // 2. 기존 이미지 삭제 (같은 파트타이머 ID에 대해)
            uploadImageRepository.findByPartTimerId(partTimerId).ifPresent(existingImage -> {
                deleteFile(existingImage.getFilename());
                uploadImageRepository.delete(existingImage);
            });

            // 3. 데이터베이스에 새 이미지 정보 저장
            UploadImageEntity newImage = UploadImageEntity.builder()
                    .filename(newFilename)
                    .partTimerId(partTimerId)
                    .build();
            return uploadImageRepository.save(newImage);

        } catch (IOException e) {
            throw new RuntimeException("파일 업로드 실패: " + e.getMessage(), e);
        }
    }

    /**
     * 특정 파트타이머의 이미지 조회
     */
    @Transactional(readOnly = true)
    public Optional<UploadImageEntity> getImageByPartTimerId(Long partTimerId) {
        return uploadImageRepository.findByPartTimerId(partTimerId);
    }

    /**
     * 특정 파트타이머의 이미지 삭제
     */
    @Transactional
    public boolean deleteImageByPartTimerId(Long partTimerId) {
        Optional<UploadImageEntity> imageOptional = uploadImageRepository.findByPartTimerId(partTimerId);
        if (imageOptional.isPresent()) {
            UploadImageEntity image = imageOptional.get();
            deleteFile(image.getFilename());
            uploadImageRepository.delete(image);
            return true;
        }
        return false;
    }

    /**
     * 파일 삭제 메서드
     */
    private void deleteFile(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).toAbsolutePath();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + e.getMessage(), e);
        }
    }
}
