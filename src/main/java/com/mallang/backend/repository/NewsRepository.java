package com.mallang.backend.repository;

import com.mallang.backend.domain.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    // 추가적인 쿼리 메서드 정의 가능
}