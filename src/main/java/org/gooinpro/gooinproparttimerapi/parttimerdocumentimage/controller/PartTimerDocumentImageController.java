package org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.dto.PartTimerDocumentImageRegisterDTO;
import org.gooinpro.gooinproparttimerapi.parttimerdocumentimage.service.PartTimerDocumentImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/part/api/v1/documentimage")
@Log4j2
@RequiredArgsConstructor
public class PartTimerDocumentImageController {

    private final PartTimerDocumentImageService partTimerDocumentImageService;

    @PostMapping("/add")
    public ResponseEntity<String> partTimerDocumentImageRegister(@RequestBody PartTimerDocumentImageRegisterDTO partTimerDocumentImageRegisterDTO) {
        log.info("partTimerDocumentImageRegister controller");

        partTimerDocumentImageService.partTimerDocumentImageRegister(partTimerDocumentImageRegisterDTO);

        return ResponseEntity.ok("success register");
    }
}
