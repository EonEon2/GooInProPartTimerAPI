package org.gooinpro.gooinproparttimerapi.parttimer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerDetailDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.dto.PartTimerRegiserDTO;
import org.gooinpro.gooinproparttimerapi.parttimer.service.PartTimerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/part/api/v1/part")
@RequiredArgsConstructor
@Log4j2
public class PartTimerController {

    private final PartTimerService partTimerService;

    // 개인정보 조회
    @GetMapping("/detail")
    public ResponseEntity<PartTimerDetailDTO> getPartTimerDetail(
            @RequestParam Long pno) {
        return ResponseEntity.ok(partTimerService.getOnePartTimerInfo(pno));
    }

    // 개인정보 입력/수정
    @PutMapping("/edit")
    public ResponseEntity<Void> editPartTimerInfo(
            @RequestParam Long pno,
            @Valid @RequestBody PartTimerRegiserDTO dto) {
        partTimerService.editPartTimerInfo(pno, dto);
        return ResponseEntity.ok().build();
    }

    // 계정 삭제
    @PutMapping("/account/deactivate")
    public ResponseEntity<Void> deactivateAccount(
            @RequestParam Long pno) {
        partTimerService.deletePartTimerAccount(pno);
        return ResponseEntity.ok().build();
    }
}
