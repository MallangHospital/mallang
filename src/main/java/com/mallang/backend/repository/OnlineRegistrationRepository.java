package com.mallang.backend.repository;

import com.mallang.backend.domain.OnlineRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineRegistrationRepository extends JpaRepository<OnlineRegistration, Long> {
    // 추가적인 쿼리 메서드를 필요에 맞게 정의할 수 있습니다.
}