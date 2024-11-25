package com.mallang.backend.repository;

import com.mallang.backend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * 특정 의사에 대한 리뷰 조회
     *
     * @param doctorId 의사 ID
     * @return 해당 의사에 대한 리뷰 리스트
     */
    List<Review> findByDoctorId(Long doctorId);

    /**
     * 특정 진료과에 대한 리뷰 조회
     *
     * @param departmentId 진료과 ID
     * @return 해당 진료과에 대한 리뷰 리스트
     */
    List<Review> findByDepartmentId(Long departmentId);

    /**
     * 특정 사용자에 대한 리뷰 조회
     *
     * @param memberId 사용자 ID
     * @return 해당 사용자에 대한 리뷰 리스트
     */
    List<Review> findByMemberId(Long memberId);

    /**
     * 전체 별점 평균 계산
     *
     * @return 전체 별점 평균
     */
    @Query("SELECT AVG(r.star) FROM Review r")
    Double calculateOverallAverageRating();

    /**
     * 세분화된 특정 별점 평균 계산
     *
     * @param index 세분화된 별점의 인덱스 (0: 설명, 1: 치료 결과 등)
     * @return 해당 세분화된 별점의 평균
     */
    @Query("SELECT AVG(CAST(ELT(?1+1, r.detailStars[0], r.detailStars[1], r.detailStars[2], r.detailStars[3]) AS double)) FROM Review r")
    Double calculateDetailAverageRating(int index);
}