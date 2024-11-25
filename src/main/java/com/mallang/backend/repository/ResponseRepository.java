package com.mallang.backend.repository;

import com.mallang.backend.domain.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findByMemberId(String memberId);
}