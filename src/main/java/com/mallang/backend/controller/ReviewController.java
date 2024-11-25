package com.mallang.backend.controller;

import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.service.ReviewService;
import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<ReviewDTO> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @PostMapping
    public ReviewDTO createReview(@RequestBody ReviewDTO reviewDTO, @RequestParam Long doctorId, @RequestParam Long memberId) {
        // 의사와 회원을 DB에서 조회하는 로직
        Doctor doctor = new Doctor(); // 의사 조회 로직 필요
        Member member = new Member(); // 회원 조회 로직 필요

        // 실제 구현에서는 doctorId와 memberId를 사용하여 의사와 회원을 DB에서 조회해야 함
        return reviewService.createReview(reviewDTO, doctor, member);
    }
}