package com.mallang.backend.repository;

import com.mallang.backend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    // 기존 메서드들은 유지합니다...

    /**
     * 특정 진료과 ID 존재 여부 확인
     *
     * @param departmentId 진료과 ID
     * @return 존재 여부
     */
    boolean existsByDepartmentId(Long departmentId);

    /**
     * 특정 의사 ID 존재 여부 확인
     *
     * @param doctorId 의사 ID
     * @return 존재 여부
     */
    boolean existsByDoctorId(Long doctorId);

    /**
     * 특정 사용자 ID 존재 여부 확인
     *
     * @param memberId 사용자 ID
     * @return 존재 여부
     */
    boolean existsByMemberId(Long memberId);
}