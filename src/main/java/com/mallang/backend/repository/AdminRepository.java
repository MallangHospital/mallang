package com.mallang.backend.repository;

import com.mallang.backend.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByAdminName(String AdminName);

    Optional<Admin> findByAdminId(String AdminId);

    // username과 password로 Admin 인증
    Optional<Admin> findByAdminIdAndPassword(String id, String password);


}