package org.gooinpro.gooinproparttimerapi.workplace.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.common.dto.PageRequestDTO;
import org.gooinpro.gooinproparttimerapi.common.dto.PageResponseDTO;
import org.gooinpro.gooinproparttimerapi.workplace.dto.WorkPlaceListDTO;
import org.gooinpro.gooinproparttimerapi.workplace.service.WorkPlaceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/parttimer/api/v1/workplace")
@Log4j2
@RequiredArgsConstructor
public class WorkPlaceController {

    private final WorkPlaceService workPlaceService;
    private final RestTemplate restTemplate;

    @Value("${NAVER_MAP_API_CLIENT_ID}")
    private String NAVER_MAP_API_CLIENT_ID;

    @Value("${NAVER_MAP_API_CLIENT_SECRET}")
    private String NAVER_MAP_API_CLIENT_SECRET;

    @Value("${NAVER_MAP_API_URL}")
    private String NAVER_MAP_API_URL;


    @GetMapping("list")
    public ResponseEntity<PageResponseDTO<WorkPlaceListDTO>> placeList(PageRequestDTO pageRequestDTO) {
        log.info("place List");
        return ResponseEntity.ok(workPlaceService.WorkPlaceList(pageRequestDTO));
    }

//    @GetMapping("marker")
//    public ResponseEntity<Map<String, Object>> getMarkers(@RequestParam String[] addresses) {
//        try {
//            // 여러 개의 주소를 로그로 출력
//            log.info("getMarkers addresses: " + Arrays.toString(addresses));
//
//            // 여러 주소에 대한 결과를 저장할 맵
//            Map<String, Object> result = new HashMap<>();
//
//            // 각 주소에 대해 마커 정보 요청
//            for (String address : addresses) {
//                String url = NAVER_MAP_API_URL + address;
//                log.info("url: " + url);
//
//                HttpHeaders headers = new HttpHeaders();
//                headers.set("x-ncp-apigw-api-key-id", NAVER_MAP_API_CLIENT_ID);
//                headers.set("x-ncp-apigw-api-key", NAVER_MAP_API_CLIENT_SECRET);
//
//                // 각 주소에 대해 REST API 요청
//                ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET,
//                        new org.springframework.http.HttpEntity<>(headers), new ParameterizedTypeReference<Map<String, Object>>() {});
//
//                // 결과를 맵에 추가 (주소별로 구분해서 저장)
//                result.put(address, response.getBody());
//            }
//
//            return ResponseEntity.ok(result);
//        } catch (Exception e) {
//            log.error(e);
//            return ResponseEntity.status(500).body(Map.of("error", "Error processing request"));
//        }
//    }
}
