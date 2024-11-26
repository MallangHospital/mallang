package com.mallang.backend.service;

import com.mallang.backend.dto.ReviewDTO;
import com.mallang.backend.domain.Review;
import com.mallang.backend.repository.DepartmentRepository;
import com.mallang.backend.repository.DoctorRepository;
import com.mallang.backend.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         DepartmentRepository departmentRepository,
                         DoctorRepository doctorRepository) {
        this.reviewRepository = reviewRepository;
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
    }

    // 리뷰 저장
    private void saveReview(ReviewDTO reviewDTO, String fileUrl) {
        Review review = new Review();
        review.setMemberId(reviewDTO.getMemberId());
        review.setDoctorId(reviewDTO.getDoctorId());
        review.setDepartmentId(reviewDTO.getDepartmentId());
        review.setDetailStars(reviewDTO.getDetailStar());
        review.setContent(reviewDTO.getContent());
        review.setFileUrl(fileUrl);
        review.setMemberPassword(reviewDTO.getMemberPassword());
        reviewRepository.save(review);
    }

    // 리뷰 생성
    public void createReview(ReviewDTO reviewDTO, MultipartFile file) {
        String fileUrl = (file != null && !file.isEmpty()) ? saveFile(file) : null;
        saveReview(reviewDTO, fileUrl);
    }

    // 모든 리뷰 조회
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 리뷰 삭제
    public void deleteReviewById(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다.");
        }
        reviewRepository.deleteById(id);
    }

    // 비밀번호 검증
    public boolean isPasswordValidForReview(Long id, String password) {
        return reviewRepository.findById(id)
                .map(review -> review.getMemberPassword().equals(password))
                .orElse(false);
    }

    // 유효성 검사 메서드 추가
    public boolean isValidDepartment(Long departmentId) {
        return reviewRepository.existsByDepartmentId(departmentId);
    }



    // 의사 유효성 확인
    public boolean isValidDoctor(Long doctorId) {
        return doctorRepository.existsById(doctorId);
    }

    // 세분화된 별점 평균 계산
    public Map<String, Double> calculateDetailAverages() {
        Map<String, Integer> detailIndexMap = Map.of(
                "description", 0,
                "treatment", 1,
                "staff", 2,
                "cleanliness", 3
        );

        Map<String, Double> detailAverages = new HashMap<>();
        List<Review> reviews = reviewRepository.findAll();

        for (Map.Entry<String, Integer> entry : detailIndexMap.entrySet()) {
            String key = entry.getKey();
            int index = entry.getValue();

            double average = reviews.stream()
                    .map(Review::getDetailStars)
                    .filter(stars -> stars.size() > index)
                    .mapToDouble(stars -> stars.get(index))
                    .average()
                    .orElse(0.0);

            detailAverages.put(key, average);
        }

        return detailAverages;
    }

    // 파일 저장
    private String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            Path uploadDir = Paths.get("uploads");

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID() + "_" + originalFileName;

            Path targetLocation = uploadDir.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation);

            return targetLocation.toString();
        } catch (IOException ex) {
            throw new RuntimeException("파일 업로드 실패", ex);
        }
    }

    // 엔티티 -> DTO 변환
    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setMemberId(review.getMemberId());
        dto.setDoctorId(review.getDoctorId());
        dto.setDepartmentId(review.getDepartmentId());
        dto.setDetailStar(review.getDetailStars());
        dto.setContent(review.getContent());
        dto.setAttachment(review.getFileUrl());
        dto.setCreatedAt(review.getCreatedDate());
        return dto;
    }
}