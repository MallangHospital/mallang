package com.mallang.backend.repository;

import com.mallang.backend.domain.OnlineRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnlineRegistrationRepository extends JpaRepository<OnlineRegistration, Long> {
}