package com.mallang.backend.controller;

import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 생성
    @PostMapping("/new")
    public ResponseEntity<String> createReview(@RequestBody @Validated ReviewDTO reviewDTO) {
        System.out.println("Received ReviewDTO: " + reviewDTO.toString());
        try {
            String validationError = validateReview(reviewDTO);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            reviewService.createReview(reviewDTO, null);
            return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("리뷰 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private String validateReview(ReviewDTO reviewDTO) {
        if (!reviewService.isValidDepartment(reviewDTO.getDepartmentId())) {
            return "유효하지 않은 진료과 ID입니다.";
        }
        if (!reviewService.isValidDoctor(reviewDTO.getDoctorId())) {
            return "유효하지 않은 의사 ID입니다.";
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        Map<String, Double> averages = reviewService.calculateDetailAverages();
        Map<String, Object> response = new HashMap<>();
        response.put("reviews", reviews);
        response.put("averages", averages);
        return ResponseEntity.ok(response);
    }
}