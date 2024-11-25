package com.mallang.backend.repository;

import com.mallang.backend.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    // 추가적인 쿼리 메서드 정의 가능
}