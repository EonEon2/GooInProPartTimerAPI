package org.gooinpro.gooinproparttimerapi.parttimer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerRegiserDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.gooinpro.gooinproparttimerapi.parttimerimage.domain.PartTimerImageEntity;
import org.gooinpro.gooinproparttimerapi.parttimerimage.repository.PartTimerImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerDetailDTO;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class PartTimerService {

    private final PartTimerRepository partTimerRepository;
    private final PartTimerImageRepository partTimerImageRepository;

    // 개인정보 조회
    public PartTimerDetailDTO getOnePartTimerInfo(Long pno) {
        PartTimerEntity partTimer = partTimerRepository.findByPnoAndPdeleteIsFalse(pno)
                .orElseThrow(() -> new RuntimeException("조회할 사용자를 찾을 수 없습니다."));

        // 프로필 이미지 정보 조회
        String profileImageUrl = "";
        try {
            // 가장 최근에 업로드된 이미지 파일명 조회
            PartTimerImageEntity latestImage = partTimerImageRepository
                    .findTopByPartTimerOrderByPinoDesc(partTimer)
                    .orElse(null);

            if (latestImage != null) {
                // 이미지 URL 생성 (서버 설정에 따라 경로 조정 필요)
                profileImageUrl = "/uploads/profile/" + latestImage.getPifilename();
            }
        } catch (Exception e) {
            log.error("프로필 이미지 조회 중 오류 발생: " + e.getMessage());
        }

        return PartTimerDetailDTO.builder()
                .pno(partTimer.getPno())
                .pemail(partTimer.getPemail())
                .pname(partTimer.getPname())
                .pbirth(partTimer.getPbirth())
                .pgender(partTimer.isPgender())
                .proadAddress(partTimer.getProadAddress())
                .pdetailAddress(partTimer.getPdetailAddress())
                .pregdate(partTimer.getPregdate())
                .profileImageUrl(profileImageUrl) // 프로필 이미지 URL 설정
                .build();
    }


    // 개인정보 등록/수정
    public void editPartTimerInfo(Long pno, PartTimerRegiserDTO dto) {
        PartTimerEntity partTimer = partTimerRepository.findByPnoAndPdeleteIsFalse(pno)
                .orElseThrow(() -> new RuntimeException("수정할 사용자를 찾을 수 없습니다."));

        // 정보 업데이트
        partTimer.setPname(dto.getPname());
        partTimer.setPbirth(dto.getPbirth());
        partTimer.setPgender(dto.isPgender());
        partTimer.setProadAddress(dto.getProadAddress());
        partTimer.setPdetailAddress(dto.getPdetailAddress());


        partTimerRepository.save(partTimer);
    }

    // 계정 삭제 (소프트 딜리트)
    public void deletePartTimerAccount(Long pno) {
        PartTimerEntity partTimer = partTimerRepository.findByPnoAndPdeleteIsFalse(pno)
                .orElseThrow(() -> new RuntimeException("삭제할 사용자를 찾을 수 없습니다."));

        partTimer.setPdelete(true);
        partTimerRepository.save(partTimer);
    }
}
