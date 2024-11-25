package com.mallang.backend.service;

import com.mallang.backend.domain.Doctor;
import com.mallang.backend.domain.Member;
import com.mallang.backend.domain.Review;
import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    // 모든 리뷰를 가져오는 메서드
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(review -> {
                    ReviewDTO dto = new ReviewDTO();
                    dto.setId(review.getId());  // 글번호
                    dto.setContent(review.getContent());  // 리뷰 내용
                    dto.setStar(review.getStar());  // 평점
                    dto.setDoctorId(review.getDoctor().getId());  // 의사 ID
                    dto.setMid((review.getMember().getMid()));  // 작성자 ID
                    dto.setAnswerRate(calculateAnswerRate(review));  // 답변율 계산
                    dto.setOverallRating(calculateOverallRating(review));  // 전체 평점 계산
                    dto.setConsultationMethod(review.getConsultationMethod());  // 상담 경로
                    dto.setAnswer(review.getAnswer());  // 답변
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 리뷰를 생성하는 메서드
    public ReviewDTO createReview(ReviewDTO reviewDTO, Doctor doctor, Member member) {
        Review review = new Review();
        review.setContent(reviewDTO.getContent());  // 리뷰 내용
        review.setStar(reviewDTO.getStar());  // 평점
        review.setDoctor(doctor);  // 의사
        review.setMember(member);  // 작성자
        review.setConsultationMethod(reviewDTO.getConsultationMethod());  // 상담 경로
        review.setAnswer(reviewDTO.getAnswer());  // 답변
        review = reviewRepository.save(review);  // 저장

        // 저장된 Review 객체를 DTO로 변환하여 반환
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());  // 글번호
        dto.setContent(review.getContent());  // 리뷰 내용
        dto.setStar(review.getStar());  // 평점
        dto.setDoctorId(review.getDoctor().getId());  // 의사 ID
        dto.setMid((review.getMember().getMid()));  // 작성자 ID
        dto.setAnswerRate(calculateAnswerRate(review));  // 답변율 계산
        dto.setOverallRating(calculateOverallRating(review));  // 전체 평점 계산
        dto.setConsultationMethod(review.getConsultationMethod());  // 상담 경로
        dto.setAnswer(review.getAnswer());  // 답변
        return dto;
    }

    // 답변율 계산 (필드만 반환하는 방식)
    private double calculateAnswerRate(Review review) {
        // 예시로, Review 클래스에 답변 여부가 필드로 존재한다고 가정하여 간단히 반환
        return review.getAnswerRate();  // Review 객체에 답변율에 해당하는 필드가 있다고 가정
    }

    // 전체 평점 계산 (필드만 반환하는 방식)
    private double calculateOverallRating(Review review) {
        // Review 클래스에 전체 평점이 저장되어 있다고 가정
        return review.getOverallRating();  // Review 객체에서 평점 필드 값을 바로 반환
    }
}