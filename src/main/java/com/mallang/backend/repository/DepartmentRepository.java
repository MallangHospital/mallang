package com.mallang.backend.repository;

import com.mallang.backend.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // 부서 이름으로 부서를 조회하는 메서드
    Optional<Department> findByName(String name);

}