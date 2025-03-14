package org.gooinpro.gooinproparttimerapi.parttimerimage.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.parttimerimage.dto.PartTimerImageRegisterDTO;
import org.gooinpro.gooinproparttimerapi.parttimerimage.service.PartTimerImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/part/api/v1/image")
@Log4j2
@RequiredArgsConstructor
public class PartTimerImageController {
    private final PartTimerImageService partTimerImageService;

    @PostMapping("/add")
    public ResponseEntity<String> partTimerImageRegister(@RequestBody PartTimerImageRegisterDTO partTimerImageRegisterDTO) {

        log.info("partTimerImageRegister");

        log.info("partTimerImageRegisterDTO: " + partTimerImageRegisterDTO);

        partTimerImageService.partTimerImageRegister(partTimerImageRegisterDTO);

        return ResponseEntity.ok("success register");
    }

    @GetMapping("/get/{pno}")
    public ResponseEntity<List<String>> getPartTimerImages(@PathVariable Long pno) {
        log.info("조회된 이미지 개수: {}", partTimerImageService.getPartTimerImages(pno).size());
        return ResponseEntity.ok(partTimerImageService.getPartTimerImages(pno));
    }
}
