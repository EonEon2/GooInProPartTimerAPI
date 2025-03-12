package org.gooinpro.gooinproparttimerapi.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.review.dto.ReviewDTO;
import org.gooinpro.gooinproparttimerapi.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/part/api/v1/employee/review")
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PutMapping("/create")
    public ResponseEntity<Void> createReview(@RequestBody ReviewDTO reviewDTO) {
        log.info("Creating review for employer: {}", reviewDTO);
        reviewService.createReview(reviewDTO);
        return ResponseEntity.ok().build();
    }

    // 리뷰 리스트 (키워드 또는 고용주 ID로 검색)
    @GetMapping("/list")
    public ResponseEntity<List<ReviewDTO>> getReviewList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long eno,
            @RequestParam(required = false) Long pno) {
        log.info("Searching reviews with keyword: {}, eno: {}, pno: {}", keyword, eno, pno);
        List<ReviewDTO> reviewList = reviewService.getReviewList(keyword, eno, pno);
        return ResponseEntity.ok(reviewList);
    }
}
