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

    /**
     * 리뷰 생성
     */
    public void createReview(ReviewDTO reviewDTO, MultipartFile file) {
        String fileUrl = saveFile(file);

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

    /**
     * 모든 리뷰 조회
     */
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * 리뷰 삭제
     */
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);
    }

    /**
     * 진료과 유효성 확인
     */
    public boolean isValidDepartment(String departmentName) {
        return departmentRepository.findByName(departmentName).isPresent();
    }

    /**
     * 의사 유효성 확인
     */
    public boolean isValidDoctor(Long doctorId) {
        return doctorRepository.existsById(doctorId);
    }

    /**
     * 리뷰 비밀번호 확인
     */
    public boolean isPasswordValidForReview(Long id, String password) {
        reviewRepository.findById(id)
                .map(review -> false);
        return false;
    }

    /**
     * 리뷰 통계 계산
     */
    public Map<String, Object> calculateReviewStatistics() {
        List<Review> reviews = reviewRepository.findAll();

        long totalReviewCount = reviews.size();
        double overallRatingSum = reviews.stream()
                .mapToDouble(review -> review.getDetailStars().stream().mapToDouble(Integer::doubleValue).average().orElse(0.0))
                .sum();
        double overallRatingAverage = totalReviewCount > 0 ? overallRatingSum / totalReviewCount : 0;

        Map<String, Double> detailRatingAverages = calculateDetailAverages(reviews);

        Map<String, Object> result = new HashMap<>(detailRatingAverages);
        result.put("totalReviewCount", totalReviewCount);
        result.put("overallRatingAverage", overallRatingAverage);

        return result;
    }


     //특정 키에 대한 세분화된 별점 평균 계산
     //단일 키에 대해 별점 평균을 계산합니다. 호출 시 키를 직접 전달해야 합니다.
    public double calculateDetailAverage(String key) {
        // Key와 인덱스를 매핑 (key에 따라 detailStars의 특정 위치 값 선택)
        Map<String, Integer> detailIndexMap = Map.of(
                "자세한 설명", 0,
                "치료 후 결과", 1,
                "직원의 친절", 2,
                "청결함", 3
        );

        // Key에 해당하는 인덱스를 가져옴
        Integer index = detailIndexMap.get(key);
        if (index == null) {
            throw new IllegalArgumentException("Invalid key for detail stars: " + key);
        }

        // 해당 인덱스의 평균 계산
        return reviewRepository.findAll().stream()
                .map(Review::getDetailStars)  // List<Integer> 가져오기
                .filter(stars -> stars.size() > index)  // 유효한 인덱스인지 확인
                .mapToDouble(stars -> stars.get(index))  // 해당 인덱스의 별점 선택
                .average()
                .orElse(0.0);  // 리뷰가 없을 경우 0.0 반환
    }


     // 세분화된 별점 평균 계산
     // 모든 키에 대해 별점 평균을 계산하여 한꺼번에 반환합니다.
    public Map<String, Double> calculateDetailAverages(List<Review> reviews) {
        // Key와 detailStars 인덱스를 매핑
        Map<String, Integer> detailIndexMap = Map.of(
                "자세한 설명", 0,
                "치료 후 결과", 1,
                "직원의 친절", 2,
                "청결함", 3
        );

        Map<String, Double> detailAverages = new HashMap<>();

        for (String key : detailIndexMap.keySet()) {
            int index = detailIndexMap.get(key);  // Key에 해당하는 인덱스 가져오기

            double average = reviews.stream()
                    .map(Review::getDetailStars) // 각 리뷰의 detailStars 가져오기
                    .filter(stars -> stars.size() > index) // 유효한 인덱스인지 확인
                    .mapToDouble(stars -> stars.get(index)) // 해당 인덱스의 값 가져오기
                    .average()
                    .orElse(0.0); // 평균 계산 (없으면 0.0)

            detailAverages.put(key, average); // 결과를 Map에 추가
        }

        return detailAverages;
    }

    /**
     * 리뷰 개수 조회
     */
    public long getTotalReviewCount() {
        return reviewRepository.count();
    }

    /**
     * 파일 저장
     */
    private String saveFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path targetLocation = Paths.get("uploads").resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation);
            return targetLocation.toString();
        } catch (IOException ex) {
            throw new RuntimeException("파일 업로드 실패", ex);
        }
    }

    /**
     * 엔티티 -> DTO 변환
     */
    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setMemberId(review.getMemberId());
        dto.setDoctorId(review.getDoctorId());
        dto.setDepartmentId(review.getDepartmentId());
        dto.setDetailStar(review.getDetailStars());
        dto.setContent(review.getContent());
        dto.setAttachment(review.getFileUrl());
        dto.setCreatedAt(review.getCreatedDate().toEpochSecond(ZoneOffset.UTC));
        return dto;
    }
}