package org.gooinpro.gooinproparttimerapi.upload.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gooinpro.gooinproparttimerapi.upload.domain.UploadImageEntity;
import org.gooinpro.gooinproparttimerapi.upload.dto.UploadImageDTO;
import org.gooinpro.gooinproparttimerapi.upload.service.UploadImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/part/api/v1/upload")
@RequiredArgsConstructor
public class UploadImageController {

    private final UploadImageService uploadImageService;

    // 파트타이머 프로필 이미지 업로드
    @PostMapping("/partTimer/profile")
    public ResponseEntity<UploadImageDTO> uploadPartTimerProfileImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("pno") Long partTimerId) {
        log.info("프로필 이미지 업로드 요청 받음 - 파트타이머 ID: {}", partTimerId);
        log.info("업로드된 파일 정보 - 이름: {}, 크기: {} bytes", file.getOriginalFilename(), file.getSize());

        UploadImageEntity uploadedImage = uploadImageService.uploadImage(file, partTimerId);
        log.info("이미지 업로드 완료 - 저장된 파일명: {}", uploadedImage.getFilename());

        UploadImageDTO imageDTO = UploadImageDTO.builder()
                .imageId(uploadedImage.getPino())
                .filename(uploadedImage.getFilename())
                .partTimerId(uploadedImage.getPartTimerId())
                .imageUrl("/uploads/" + uploadedImage.getFilename())
                .build();

        log.info("이미지 업로드 응답 생성 완료 - 이미지 ID: {}", imageDTO.getImageId());
        return ResponseEntity.status(HttpStatus.CREATED).body(imageDTO);
    }

    // 파트타이머 프로필 이미지 조회
    @GetMapping("/partTimer/profile/{pno}")
    public ResponseEntity<UploadImageDTO> getPartTimerProfileImage(@PathVariable("pno") Long partTimerId) {
        log.info("프로필 이미지 조회 요청 받음 - 파트타이머 ID: {}", partTimerId);

        Optional<UploadImageEntity> imageOptional = uploadImageService.getImageByPartTimerId(partTimerId);
        if (imageOptional.isPresent()) {
            UploadImageEntity image = imageOptional.get();
            UploadImageDTO imageDTO = UploadImageDTO.builder()
                    .imageId(image.getPino())
                    .filename(image.getFilename())
                    .partTimerId(image.getPartTimerId())
                    .imageUrl("/uploads/" + image.getFilename())
                    .build();
            log.info("프로필 이미지 조회 성공 - 이미지 ID: {}", imageDTO.getImageId());
            return ResponseEntity.ok(imageDTO);
        } else {
            log.warn("프로필 이미지를 찾을 수 없음 - 파트타이머 ID: {}", partTimerId);
            return ResponseEntity.notFound().build();
        }
    }

    // 파트타이머 프로필 이미지 삭제
    @DeleteMapping("/partTimer/profile/{pno}")
    public ResponseEntity<Void> deletePartTimerProfileImage(@PathVariable("pno") Long partTimerId) {
        log.info("프로필 이미지 삭제 요청 받음 - 파트타이머 ID: {}", partTimerId);

        boolean deleted = uploadImageService.deleteImageByPartTimerId(partTimerId);
        if (deleted) {
            log.info("프로필 이미지 삭제 성공 - 파트타이머 ID: {}", partTimerId);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("삭제할 프로필 이미지를 찾을 수 없음 - 파트타이머 ID: {}", partTimerId);
            return ResponseEntity.notFound().build();
        }
    }
}
