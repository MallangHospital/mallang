package com.mallang.backend.repository;

import com.mallang.backend.domain.HealthcareReserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HealthcareReserveRepository extends JpaRepository<HealthcareReserve, Long> {
    List<HealthcareReserve> findByMemberId(String memberId);
}