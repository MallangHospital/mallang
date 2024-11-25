package com.mallang.backend.repository;

import com.mallang.backend.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 추가적인 쿼리 메서드 정의 가능
}