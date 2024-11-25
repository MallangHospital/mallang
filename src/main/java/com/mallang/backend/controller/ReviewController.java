package com.mallang.backend.controller;

import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 생성
    @PostMapping
    public ResponseEntity<String> createReview(
            @RequestPart("review") @Validated ReviewDTO reviewDTO,
            @RequestPart("file") MultipartFile file) {

        // 1. 진료과 확인
        if (reviewDTO.getDepartmentId() == null || !reviewService.isValidDepartment(String.valueOf(reviewDTO.getDepartmentId()))) {
            return ResponseEntity.badRequest().body("유효하지 않은 진료과를 선택했습니다.");
        }

        // 2. 의사 확인
        if (reviewDTO.getDoctorId() == null || !reviewService.isValidDoctor(reviewDTO.getDoctorId())) {
            return ResponseEntity.badRequest().body("유효하지 않은 의사를 선택했습니다.");
        }

        // 3. 세분화된 별점 유효성 검사
        if (reviewDTO.getDetailStar() == null || reviewDTO.getDetailStar().size() != 4) {
            return ResponseEntity.badRequest().body("모든 세분화된 별점을 입력해주세요.");
        }

        // 4. 리뷰 내용 확인
        if (reviewDTO.getContent() == null || reviewDTO.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("리뷰 내용을 입력해주세요.");
        }

        // 5. 첨부파일 확인
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("병원 방문을 인증할 수 있는 자료를 올려주세요.");
        }

        // 모든 유효성 검사 통과 시 리뷰 생성 로직 호출
        reviewService.createReview(reviewDTO, file);
        return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
    }

    // 모든 리뷰 조회
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();

        // 세분화된 별점 평균 계산
        double detailDescriptionAvg = reviewService.calculateDetailAverage("description");
        double treatmentResultAvg = reviewService.calculateDetailAverage("treatment");
        double staffKindnessAvg = reviewService.calculateDetailAverage("staff");
        double cleanlinessAvg = reviewService.calculateDetailAverage("cleanliness");

        // 응답에 세분화된 별점 평균 포함
        return ResponseEntity.ok()
                .header("자세한 설명 별점 평균 별점", String.format("%.2f", detailDescriptionAvg))
                .header("치료 후 결과 별점 평균 별점", String.format("%.2f", treatmentResultAvg))
                .header("직원의 친절 평균 별점", String.format("%.2f", staffKindnessAvg))
                .header("청결함 평균 별점", String.format("%.2f", cleanlinessAvg))
                .body(reviews);
    }

    // 특정 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id, @RequestParam String password) {
        // 비밀번호 확인
        if (!reviewService.isPasswordValidForReview(id, password)) {
            return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
        }

        // 리뷰 삭제
        reviewService.deleteReviewById(id);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getReviewStatistics() {
        try {
            // 리뷰 통계 계산
            Map<String, Object> statistics = reviewService.calculateReviewStatistics();

            // 결과 반환
            return ResponseEntity.ok(statistics);  // statistics는 이미 Map<String, Object> 타입이므로 캐스팅이 필요 없음
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "통계 조회 중 오류가 발생했습니다."));
        }
    }
}