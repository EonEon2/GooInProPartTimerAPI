package org.gooinpro.gooinproparttimerapi.review.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.gooinpro.gooinproparttimerapi.employer.domain.EmployerEntity;
import org.gooinpro.gooinproparttimerapi.employer.repository.EmployerRepository;
import org.gooinpro.gooinproparttimerapi.parttimer.domain.PartTimerEntity;
import org.gooinpro.gooinproparttimerapi.parttimer.repository.PartTimerRepository;
import org.gooinpro.gooinproparttimerapi.review.domain.ReviewEntity;
import org.gooinpro.gooinproparttimerapi.review.dto.ReviewDTO;
import org.gooinpro.gooinproparttimerapi.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PartTimerRepository partTimerRepository;
    private final EmployerRepository employerRepository;

    // 리뷰 등록
    public void createReview(ReviewDTO reviewDTO) {
        PartTimerEntity partTimer = partTimerRepository.findById(reviewDTO.getPno())
                .orElseThrow(() -> new RuntimeException("파트타이머를 찾을 수 없습니다. in ReveiwService "));

        EmployerEntity employer = employerRepository.findById(reviewDTO.getEno())
                .orElseThrow(() -> new RuntimeException("고용주를 찾을 수 없습니다. in ReveiwService "));

        ReviewEntity review = ReviewEntity.builder()
                .pno(partTimer)
                .eno(employer)
                .rstart(reviewDTO.getRstart())
                .rcontent(reviewDTO.getRcontent())
                .rregdate(Timestamp.valueOf(LocalDateTime.now()))
                .rdelete(false)
                .build();

        reviewRepository.save(review);
    }

    // 리뷰 목록 조회 (키워드 검색 포함)
    @Transactional
    public List<ReviewDTO> getReviewList(String keyword) {
        List<ReviewEntity> reviewList = reviewRepository.search(keyword);

        return reviewList.stream()
                .map(review -> ReviewDTO.builder()
                        .rno(review.getRno())
                        .pno(review.getPno().getPno())
                        .eno(review.getEno().getEno())
                        .rstart(review.getRstart())
                        .rcontent(review.getRcontent())
                        .build())
                .collect(Collectors.toList());
    }
}